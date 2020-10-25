package Ferreteria.app.Aplication;

import javax.swing.JOptionPane;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;

import Ferreteria.app.view.FerreteriaView;
import Ferreteria.app.view.InicioSesion;

public class FerreteriaAplicacion {

	public static void main(String[] args) {

		Display display = Display.getDefault();

		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {

			public void run() {
				Display display = Display.getDefault();

				Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {

					public void run() {
						try {

							// close();
							InicioSesion inicioSesion= new InicioSesion();
							inicioSesion.open();
							inicioSesion.setBlockOnOpen(true);
							inicioSesion.open();
						
							Display.getCurrent().dispose();
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Cierre con exito");;
						}
					}
				});
			}
		});

	}

}

