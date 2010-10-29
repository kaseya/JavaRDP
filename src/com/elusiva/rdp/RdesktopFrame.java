/* RdesktopFrame.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.3 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/15 23:18:35 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Window for RDP session
 */
package com.elusiva.rdp;

import com.elusiva.rdp.keymapping.KeyCode_FileBased;
import com.elusiva.rdp.menu.RdpMenu;
import com.elusiva.rdp.rdp5.cliprdr.ClipChannel;
import org.apache.log4j.Logger;

import java.awt.*;

//import javax.swing.Box;

public abstract class RdesktopFrame extends Frame {
    
	private static Logger logger = Logger.getLogger(RdesktopFrame.class);

	public RdesktopCanvas canvas;

	private Rdp rdp;

	private RdpMenu menu;



    protected Options option;

    /**
     * Register the clipboard channel
     * @param c ClipChannel object for controlling clipboard mapping
     */
	public void setClip(ClipChannel c) {
		canvas.addFocusListener(c);
	}

	/**
	 * @deprecated ActionListener should be used instead.
	 */
/*
	public boolean action(Event event, Object arg) {
		if (menu != null)
			return menu.action(event, arg);
		return false;
	}
*/

	protected boolean inFullscreen = false;

    /**
     * Switch to fullscreen mode
     */
	public void goFullScreen() {
		inFullscreen = true;
	}

    /**
     * Exit fullscreen mode
     */
	public void leaveFullScreen() {
		inFullscreen = false;
	}

    /**
     * Switch in/out of fullscreen mode
     */
	public void toggleFullScreen() {
		if (inFullscreen)
			leaveFullScreen();
		else
			goFullScreen();
	}

	private boolean menuVisible = false;

    /**
     * Display the menu bar
     */
	public void showMenu(){
		if (menu == null)
			menu = new RdpMenu(this);

		if (!menuVisible && option.isMenuEnabled())
			this.setMenuBar(menu);
		canvas.repaint();
		menuVisible = true;
	}
	
    /**
     * Hide the menu bar
     */
	public void hideMenu(){
		if(menuVisible && option.isMenuEnabled()) this.setMenuBar(null);
		//canvas.setSize(this.WIDTH, this.HEIGHT);
		canvas.repaint();
		menuVisible = false;
	}
	
	/**
     * Toggle the menu on/off (show if hidden, hide if visible)
     *
	 */
	public void toggleMenu() {
		if(!menuVisible) showMenu();
		else hideMenu();
	}

    /**
     * Create a new RdesktopFrame.
     * Size defined by Options.width and Options.height
     * Creates RdesktopCanvas occupying entire frame
     */
	public RdesktopFrame() {
		super();
        this.canvas = new RdesktopCanvas_Localised(option.getWidth(), option.getHeight(), option);
        init();
    }

    public RdesktopFrame(RdesktopCanvas_Localised canvas, Options option) {
        super();
        this.canvas = canvas;
        this.option = option;
        init();
    }

    private void init() {
         setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        logger.info("Title:" + option.getWindowTitle() + "MENU" + option.isMenuEnabled());

		//Common.frame = this;

        if (option.isMenuEnabled()) {
		    menu = new RdpMenu(this);
            this.setMenuBar(menu);
        }
        this.add(this.canvas);
        setTitle(option.getWindowTitle());

		if (Constants.OS == Constants.WINDOWS)
			setResizable(false);
		// Windows has to setResizable(false) before pack,
		// else draws on the frame

		if (option.isFullscreen()) {
			goFullScreen();
			setLocation(0, 0);
		} else {// centre
			pack();
			centreWindow();
		}

		logger.info("canvas:" + canvas.getSize());
		logger.info("frame: " + getSize());
		logger.info("insets:" + getInsets());

		if (Constants.OS != Constants.WINDOWS)
			setResizable(false);
		// Linux Java 1.3 needs pack() before setResizeable

		addWindowListener(new RdesktopWindowAdapter(this, this.canvas, option));
        canvas.addFocusListener(new RdesktopFocusListener(this.canvas, option));
        if (Constants.OS == Constants.WINDOWS) {
			// redraws screen on window move
			addComponentListener(new RdesktopComponentAdapter(this.canvas, option));
		}

		canvas.requestFocus();
	}


    /**
     * Retrieve the canvas contained within this frame
     * @return RdesktopCanvas object associated with this frame
     */
	public RdesktopCanvas getCanvas() {
		return this.canvas;
	}

    /**
     * Register the RDP communications layer with this frame
     * @param rdp Rdp object encapsulating the RDP comms layer
     */
	public void registerCommLayer(Rdp rdp) {
		this.rdp = rdp;
		canvas.registerCommLayer(rdp);
	}

    /**
     * Register keymap
     * @param keys Keymapping object for use in handling keyboard events
     */
	public void registerKeyboard(KeyCode_FileBased keys) {
		canvas.registerKeyboard(keys);
	}


    /**
     * Display an error dialog with "Yes" and "No" buttons and the title "properJavaRDP error"
     * @param msg Array of message lines to display in dialog box
     * @return True if "Yes" was clicked to dismiss box
     */
	public boolean showYesNoErrorDialog(String[] msg) {

        YesNoDialog d = new YesNoDialog(this, "Error", msg);
        d.show();
		return d.retry;
	}

    /**
     * Display an error dialog with the title "properJavaRDP error"
     * @param msg Array of message lines to display in dialog box
     */
	public void showErrorDialog(String[] msg) {
		Dialog d = new OKDialog(this, "Error", msg);
		d.show();
	}

    /**
     * Notify the canvas that the connection is ready for sending messages
     */
	public void triggerReadyToSend() {
		this.show();
		canvas.triggerReadyToSend();
	}

    /**
     * Centre a window to the screen
     * @param f Window to be centred
     */
	public void centreWindow(Window f) {
		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension window_size = f.getSize();
		int x = (screen_size.width - window_size.width) / 2;
		if (x < 0)
			x = 0; // window can be bigger than screen
		int y = (screen_size.height - window_size.height) / 2;
		if (y < 0)
			y = 0; // window can be bigger than screen
		f.setLocation(x, y);
	}

    /**
     * Centre this window
     */
	public void centreWindow() {
		centreWindow(this);
	}

    public Options getOption() {
        return option;
    }

    public void disconnectPlayer() {
         if (rdp != null && rdp.isConnected()) {
            logger.info("Disconnecting ...");
            rdp.disconnect();
            logger.info("Disconnected");
        }
    }
}
