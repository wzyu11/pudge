package io.transwarp.pudge.client;

import io.transwarp.pudge.core.PudgeException;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Nirvana on 2017/12/21.
 */
public abstract class AbstractSocketPudge extends SimplePudge {

    protected Socket socket;

    protected PudgeServiceAddress serviceAddress;

    public AbstractSocketPudge(PudgeServiceAddress serviceAddress) {
        this.serviceAddress = serviceAddress;
        afterProperties();
    }

    @Override
    protected final byte[] fire(byte[] hookData) {
        try {
            prepareSocket();
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            // 发送请求
            send(hookData, output);

            // 接收请求
            return receive(input);
        } catch (IOException e) {
            handleSocketException();
            throw new PudgeException("Hook PudgeService failed.", e);
        } finally {
            finalizeSocket();
        }
    }

    @Override
    public void suicide() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new PudgeException("SocketPudge failed to suicide");
            }
        }
    }

    @Override
    public boolean isAlive() {
        return socket != null && socket.isConnected();
    }

    protected void createSocket() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(serviceAddress.getHost(), serviceAddress.getPort()), 5000);
            socket.setSoTimeout(5000);
        } catch (IOException e) {
            throw new PudgeException("Failed to create simple socket connection");
        }
    }

    protected abstract void afterProperties();

    protected abstract void prepareSocket();

    protected abstract void handleSocketException();

    protected abstract void finalizeSocket();

    private void send(byte[] hookData, DataOutputStream output) throws IOException {
        output.write(lenToBytes(hookData.length));
        output.write(hookData);
        output.flush();
    }

    private byte[] receive(DataInputStream input) throws IOException {
        long multiplier = 1;
        int length = 0;
        while (true) {
            byte b = input.readByte();
            length += (b & (byte) 127) * multiplier;
            if ((b & (byte) 128) == 0) {
                break;
            }
            multiplier *= 128;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int index;
        while ((index = input.read(buffer)) != -1) {
            baos.write(buffer, 0, index);
            length -= index;

            if (length <= 0) {
                break;
            } else if (length < 1024) {
                buffer = new byte[length];
            } else {
                buffer = new byte[1024];
            }
        }

        return baos.toByteArray();
    }

    private byte[] lenToBytes(long length) {
        byte[] bytes = new byte[4];
        int i = 0;
        while (length > 0) {
            if (i > bytes.length - 1) {
                throw new IllegalArgumentException("beyond the number range");
            }
            bytes[i] = (byte) (length % 128);
            length = length / 128;
            if (length > 0) {
                bytes[i] = (byte) (bytes[i] | (byte) 128);
            }
            i++;
        }

        byte[] result = new byte[i];
        System.arraycopy(bytes, 0, result, 0, i);
        return result;
    }

}
