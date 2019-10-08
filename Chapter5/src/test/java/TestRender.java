import exec.Render;

import java.util.concurrent.Executors;

public class TestRender {
    public static void main(String[] args) {
        Render render = new Render(Executors.newCachedThreadPool());
        render.renderPage();
    }
}
