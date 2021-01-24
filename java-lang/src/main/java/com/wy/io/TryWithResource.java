package com.wy.io;

/**
 * 编译器重写code
 * @author matthew_wu
 * @since 2020/12/9 3:51 下午
 */
public class TryWithResource {

    public static void main(String[] args) {
        try (Connection conn = new Connection()) {
            conn.sendData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


class Connection implements AutoCloseable {

    public void sendData() {
        System.out.println("sending data");
    }

    @Override
    public void close() throws Exception {
        System.out.println("closing connection");
    }

}