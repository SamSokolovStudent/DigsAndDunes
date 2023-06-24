package net.soko.digs_and_dunes.client.screen.component;

import net.minecraft.util.Mth;

/**
 * Handles smooth scrolling automatically.
 *
 * @author Ocelot
 * @since 1.0.0
 */
public final class ScrollHandler {

    public static final float DEFAULT_SCROLL_SPEED = 5;
    public static final float DEFAULT_TRANSITION_SPEED = 0.5f;
    public static final double DEFAULT_MIN_SNAP = 0.1f;

    private int width;
    private int visibleWidth;

    private double scroll;
    private double lastScroll;
    private double nextScroll;
    private float scrollSpeed;
    private float transitionSpeed;
    private double minSnap;

    public ScrollHandler(int width, int visibleWidth) {
        this.width = width;
        this.visibleWidth = visibleWidth;

        this.scroll = 0;
        this.scrollSpeed = DEFAULT_SCROLL_SPEED;
        this.transitionSpeed = DEFAULT_TRANSITION_SPEED;
        this.minSnap = DEFAULT_MIN_SNAP;
    }

    /**
     * Updates the smooth transition of scrolling.
     */
    public void update() {
        this.lastScroll = this.scroll;
        if (this.getMaxScroll() > 0) {
            double delta = this.nextScroll - this.scroll;
            if (Math.abs(delta) < this.minSnap) {
                this.scroll = this.nextScroll;
            } else {
                this.scroll += delta * this.transitionSpeed;
            }

            if (this.scroll < 0) {
                this.scroll = 0;
                this.nextScroll = 0;
            }

            if (this.scroll >= this.getMaxScroll()) {
                this.scroll = this.getMaxScroll();
                this.nextScroll = this.getMaxScroll();
            }
        }
    }

    /**
     * Handles the mouse scrolling event.
     *
     * @param amount The amount the mouse was scrolled
     */
    public boolean mouseScrolled(double maxScroll, double amount) {
        if (this.getMaxScroll() > 0) {
            float scrollAmount = (float) Math.min(Math.abs(amount), maxScroll) * this.getScrollSpeed();
            float finalScroll = (amount < 0 ? -1 : 1) * scrollAmount;
            double scroll = Mth.clamp(this.getScroll() - finalScroll, 0, this.getMaxScroll());
            if (this.getScroll() != scroll) {
                this.scroll(finalScroll);
                return true;
            }
        }
        return false;
    }

    public ScrollHandler scroll(double scrollAmount) {
        this.nextScroll -= scrollAmount;
        return this;
    }

    public int getWidth() {
        return width;
    }


    public ScrollHandler setWidth(int width) {
        this.width = width;
        this.setScroll(this.scroll);
        return this;
    }

    public int getVisibleWidth() {
        return visibleWidth;
    }

    public ScrollHandler setVisibleWidth(int visibleWidth) {
        this.visibleWidth = visibleWidth;
        this.setScroll(this.scroll);
        return this;
    }


    public double getScroll() {
        return scroll;
    }

    public ScrollHandler setScroll(double scroll) {
        this.scroll = Mth.clamp(scroll, 0, this.width - this.visibleWidth);
        this.nextScroll = this.scroll;
        this.lastScroll = this.scroll;
        return this;
    }

    public int getMaxScroll() {
        return Math.max(0, this.width - this.visibleWidth);
    }


    public float getInterpolatedScroll(float partialTicks) {
        return (float) Mth.lerp(partialTicks, this.lastScroll, this.scroll);
    }

    public float getScrollSpeed() {
        return scrollSpeed;
    }

    public ScrollHandler setScrollSpeed(float scrollSpeed) {
        this.scrollSpeed = Math.max(scrollSpeed, 0);
        return this;
    }

    public double getLastScroll() {
        return lastScroll;
    }


    public double getNextScroll() {
        return nextScroll;
    }


    public ScrollHandler setTransitionSpeed(float transitionSpeed) {
        this.transitionSpeed = transitionSpeed;
        return this;
    }


    public ScrollHandler setMinSnap(double minSnap) {
        this.minSnap = minSnap;
        return this;
    }
}
