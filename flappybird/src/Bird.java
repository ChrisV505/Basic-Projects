import java.awt.Image;

public class Bird {
        private int x;
        private int y;
        private int width;
        private int height;
        private Image img;

        Bird(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public Image getImg() {
            return img;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
