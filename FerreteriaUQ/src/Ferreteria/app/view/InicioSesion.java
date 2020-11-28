package Ferreteria.app.view;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.swt.SWTObservables;
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

import Ferreteria.app.Excepciones.UsuarioExcepcion;
import Ferreteria.app.controller.CrudInicioSesion;

public class InicioSesion extends ApplicationWindow {

	private Text textUsuario;
	private Text textContrasenia;
	String usuario= "";
	
	CrudInicioSesion crudInicioSesion= new CrudInicioSesion();
	
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
		textUsuario.setBounds(10, 54, 258, 28);
		
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
				usuario = textUsuario.getText();
				String contrasenia = textContrasenia.getText();
				
				boolean inicio;
				try{
					inicio = crudInicioSesion.iniciarSesion(usuario, contrasenia);
					if(inicio){
						crudInicioSesion.escribirEnLog("Inicio de Sesion del usuario: "+ usuario, 1, "InicioSesion");
//						crudInicioSesion.guardarRespaldoXml();
//						crudInicioSesion.guardarRespaldoBinario();
//						crudInicioSesion.guardarRespaldoCompras();
						
						
						Display display = Display.getDefault();
						Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {					
							@Override
							public void run() {
								Display display = Display.getDefault();
								Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
									
									@Override
									public void run() {
										try{
											close();
											if(usuario.equalsIgnoreCase("admin")){
												FerreteriaView ferreteriaView = new FerreteriaView();
												ferreteriaView.setBlockOnOpen(true);
												ferreteriaView.open();
											}else if(usuario.equalsIgnoreCase("empleado")){
												FerreteriaView2 ferreteriaView2 = new FerreteriaView2();
												ferreteriaView2.setBlockOnOpen(true);
												ferreteriaView2.open();
											}	
											

											Display.getCurrent().dispose();
											crudInicioSesion.escribirEnLog("Cierre de sesión del usuario: "+usuario, 1, "CierreSesión");
										}catch (Exception e){
											System.out.println("Falla aqui ee "+ e);
										}
									}
								});
								
							}
						});
					}
				}catch (FileNotFoundException e1){
					JOptionPane.showMessageDialog(null,"Archivo usuarios no existe");
			}catch (IOException e1){
				System.out.println(e1);
			}catch (UsuarioExcepcion e1){
				JOptionPane.showMessageDialog(null,"Usuario no existe");
				crudInicioSesion.escribirEnLog(1, usuario);
			}
			}
		});


		btnIniciarSesin.setBounds(86, 238, 129, 35);
		btnIniciarSesin.setText("Iniciar sesi\u00F3n.");
		return container;
	}


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			windowInicioSesion= new InicioSesion();
			windowInicioSesion.setBlockOnOpen(true);
			windowInicioSesion.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			//System.out.println("No olvidar "+ e);
			JOptionPane.showMessageDialog(null, "Cierre exitoso");
	
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}

