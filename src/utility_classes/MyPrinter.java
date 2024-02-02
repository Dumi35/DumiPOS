package utility_classes;

import java.util.ArrayList;
import java.util.List;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Window;

public class MyPrinter {

    private static final double SCREEN_TO_PRINT_DPI = 72d / 96d;

    private double scale = 1.0f;

    private Rectangle printRectangle;

    public boolean print(PrinterJob job, boolean showPrintDialog, Node node) {

        Window window = node.getScene() != null ? node.getScene().getWindow() : null;

        if (!showPrintDialog || job.showPrintDialog(window)) {

            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            double pageWidth = pageLayout.getPrintableWidth();
            double pageHeight = pageLayout.getPrintableHeight();

            PrintInfo printInfo = getPrintInfo(pageLayout);

            double printRectX = this.printRectangle.getX();
            double printRectY = this.printRectangle.getY();
            double printRectWith = this.printRectangle.getWidth();
            double printRectHeight = this.printRectangle.getHeight();

            Node oldClip = node.getClip();
            List<Transform> oldTransforms = new ArrayList<>(node.getTransforms());
            node.setClip(new javafx.scene.shape.Rectangle(printRectX, printRectY,
                    printRectWith, printRectHeight));

            int columns = printInfo.getColumnCount();
            int rows = printInfo.getRowCount();

            double localScale = printInfo.getScale();

            node.getTransforms().add(new Scale(localScale, localScale));
            node.getTransforms().add(new Translate(-printRectX, -printRectY));

            Translate gridTransform = new Translate();
            node.getTransforms().add(gridTransform);

            boolean success = true;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    gridTransform.setX(-col * pageWidth / localScale);
                    gridTransform.setY(-row * pageHeight / localScale);

                    success &= job.printPage(pageLayout, node);
                }
            }
            node.getTransforms().clear();
            node.getTransforms().addAll(oldTransforms);
            node.setClip(oldClip);
            return success;
        }
        return false;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(final double scale) {
        this.scale = scale;
    }

    public Rectangle getPrintRectangle() {
        return printRectangle;
    }

    public void setPrintRectangle(final Rectangle printRectangle) {
        this.printRectangle = printRectangle;
    }

    public PrintInfo getPrintInfo(final PageLayout pageLayout) {

        double contentWidth = pageLayout.getPrintableWidth();
        double contentHeight = pageLayout.getPrintableHeight();

        double localScale = getScale() * SCREEN_TO_PRINT_DPI;

        final Rectangle printRect = getPrintRectangle();
        final double width = printRect.getWidth() * localScale;
        final double height = printRect.getHeight() * localScale;

        int cCount = (int) Math.ceil((width) / contentWidth);
        int rCount = (int) Math.ceil((height) / contentHeight);

        return new PrintInfo(localScale, rCount, cCount);
    }

    public static class PrintInfo {

        final double scale;
        final int rowCount;
        final int columnCount;

        public PrintInfo(final double scale, final int rowCount, final int columnCount) {
            this.scale = scale;
            this.rowCount = rowCount;
            this.columnCount = columnCount;
        }

        public double getScale() {
            return scale;
        }

        public int getRowCount() {
            return rowCount;
        }

        public int getColumnCount() {
            return columnCount;
        }
    }
    
    
}

