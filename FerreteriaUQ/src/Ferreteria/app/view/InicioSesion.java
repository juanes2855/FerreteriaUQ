package Ferreteria.app.view;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class InicioSesion extends ApplicationWindow {

	private Text textUsuario;
	private Text textContrasenia;
	
	static InicioSesion windowInicioSesion = null;
	/**
	 * Create the application window.
	 */
	public InicioSesion() {
		super(null);
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		
		Composite composite = new Composite(container, SWT.BORDER);
		composite.setBounds(55, 41, 298, 287);
		
		textUsuario = new Text(composite, SWT.BORDER);
		textUsuario.setBounds(10, 54, 258, 21);
		
		Label lblUsuario = new Label(composite, SWT.NONE);
		lblUsuario.setBounds(100, 20, 103, 28);
		lblUsuario.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblUsuario.setText("Usuario :");
		
		Label lblContrasea = new Label(composite, SWT.NONE);
		lblContrasea.setBounds(74, 113, 160, 35);
		lblContrasea.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblContrasea.setText("Contrase\u00F1a :");
		
		textContrasenia = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		textContrasenia.setBounds(10, 154, 258, 28);
		
		Label label = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 181, 258, 2);
		
		Button btnIniciarSesin = new Button(composite, SWT.NONE);
		btnIniciarSesin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String usuario = textUsuario.getText();
				String contrasenia = textContrasenia.getText();
			}
		});
		btnIniciarSesin.setBounds(86, 238, 129, 35);
		btnIniciarSesin.setText("Iniciar sesi\u00F3n.");
		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			InicioSesion window = new InicioSesion();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Inicio de Sesi\u00F3n");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 427);
	}
}

