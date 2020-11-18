package Ferreteria.app.view;

import javax.swing.JOptionPane;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import Ferreteria.app.controller.ModelFactoryController;
import Ferreteria.app.Excepciones.EstaVinculado;
import Ferreteria.app.Excepciones.numeroErroneo;
import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.controller.CrudEmpleadoViewController;
import Ferreteria.app.model.Ferreteria;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import Ferreteria.app.model.Empleado;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;

public class EmpleadoView extends Composite {

	CrudEmpleadoViewController crudEmpleadoViewController = new CrudEmpleadoViewController();
	Ferreteria ferreteria = crudEmpleadoViewController.getFerreteria();
	ModelFactoryController model = new ModelFactoryController();

	// Tamaño composite 938x587
	private DataBindingContext dataBindingContext = null;
	private Table table;
	private Text textNombreEmpleado;
	private Text textCargoEmpleado;
	private Text textCodigoEmpleado;
	private Text textSalarioEmpleado;
	private Text textDireccionEmpleado;
	private TableViewer tableViewerEmpleado;
	private Text text_busqueda;
	String [] botones = { "  Si", " No" };
	private String busquedad = "";
	Empleado empleadoSeleccionado;
	private int codigoAnterior;

	public EmpleadoView(Composite parent, int style) {
		super(parent, style);

		Group grpAcciones = new Group(this, SWT.NONE);
		grpAcciones.setText("Acciones");
		grpAcciones.setBounds(10, 10, 918, 92);

		Button btnNuevoEmpleado = new Button(grpAcciones, SWT.NONE);
		btnNuevoEmpleado.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				limpiarCampoTexto();

			}
		});
		btnNuevoEmpleado.setBounds(504, 34, 173, 35);
		btnNuevoEmpleado.setText("Nuevo Empleado");

		Button btnEliminarempleado = new Button(grpAcciones, SWT.NONE);
		btnEliminarempleado.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Boolean flag = false;
				if (empleadoSeleccionado != null) {
					if(!crudEmpleadoViewController.consultarSiExiste(empleadoSeleccionado)){
						int variable=JOptionPane.showOptionDialog (null, " Confirmar eliminación", "Confirmación", 
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null/*icono*/, botones, botones[0]);
				    if(variable==0){
					flag = crudEmpleadoViewController.eliminarEmpleado(empleadoSeleccionado.getCodigoEmpleado());
					limpiarCampoTexto();
					initDataBindings();
					crudEmpleadoViewController.salvarDatos();
					crudEmpleadoViewController.guardaTextoPlano();
					crudEmpleadoViewController.guardarArchivoLog("Se ha eliminado el empleado: "+ empleadoSeleccionado.getNombreEmpleado(), 
							2, "EmpleadoEliminado");
				    }
				    else{
				    flag=false;
				    }
					if (flag == true) {
						JOptionPane.showMessageDialog(null, "El empleado se elimino con exito");
					} else {
						JOptionPane.showMessageDialog(null, "No se eliminó");
					}
				}else{
					try{
						throw new EstaVinculado("Esta Vinculado con una transacción");
					}catch (EstaVinculado e4) {
						crudEmpleadoViewController.guardarArchivoLog(
								"El empleado seleccionado esta vinculado con una transacción: "
										+ empleadoSeleccionado.getNombreEmpleado()+" ",
								2, "EmpleadoVinculado");
				JOptionPane.showMessageDialog(null, "No se pudo eliminar, el empleado esta relacionado con una "
						+ "transacción.");
					}
				}
				}	
			}
		});
		btnEliminarempleado.setBounds(701, 34, 173, 35);
		btnEliminarempleado.setText("EliminarEmpleado");

		Label lblBusqueda = new Label(grpAcciones, SWT.NONE);
		lblBusqueda.setBounds(24, 44, 81, 25);
		lblBusqueda.setText("Busqueda");

		

		Group grpListaEmpleados = new Group(this, SWT.NONE);
		grpListaEmpleados.setText("Lista Empleados");
		grpListaEmpleados.setBounds(10, 108, 918, 254);

		tableViewerEmpleado = new TableViewer(grpListaEmpleados, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewerEmpleado.getTable();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				empleadoSeleccionado = (Empleado) table.getItem(table.getSelectionIndex()).getData();

				textNombreEmpleado.setText(empleadoSeleccionado.getNombreEmpleado());
				textCargoEmpleado.setText(empleadoSeleccionado.getCargo());
				textCodigoEmpleado.setText(Integer.toString(empleadoSeleccionado.getCodigoEmpleado()));
				textDireccionEmpleado.setText(empleadoSeleccionado.getDireccion());
				textSalarioEmpleado.setText(Double.toString(empleadoSeleccionado.getSalario()));
				codigoAnterior=empleadoSeleccionado.getCodigoEmpleado();
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(10, 29, 903, 206);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewerEmpleado, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(239);
		tblclmnNombre.setText("Nombre");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewerEmpleado, SWT.NONE);
		TableColumn tblclmnCargo = tableViewerColumn_1.getColumn();
		tblclmnCargo.setWidth(154);
		tblclmnCargo.setText("Cargo");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewerEmpleado, SWT.NONE);
		TableColumn tblclmnCodigo = tableViewerColumn_2.getColumn();
		tblclmnCodigo.setWidth(100);
		tblclmnCodigo.setText("Codigo");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewerEmpleado, SWT.NONE);
		TableColumn tblclmnSalario = tableViewerColumn_3.getColumn();
		tblclmnSalario.setWidth(152);
		tblclmnSalario.setText("Salario");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewerEmpleado, SWT.NONE);
		TableColumn tblclmnDireccin = tableViewerColumn_4.getColumn();
		tblclmnDireccin.setWidth(255);
		tblclmnDireccin.setText("Direcci\u00F3n");

		Group grpDetalles = new Group(this, SWT.NONE);
		grpDetalles.setText("Detalles");
		grpDetalles.setBounds(10, 370, 918, 206);

		Label lblNombre = new Label(grpDetalles, SWT.NONE);
		lblNombre.setBounds(27, 32, 81, 25);
		lblNombre.setText("Nombre");

		textNombreEmpleado = new Text(grpDetalles, SWT.BORDER);
		textNombreEmpleado.setBounds(114, 29, 227, 31);

		Label lblCargo = new Label(grpDetalles, SWT.NONE);
		lblCargo.setBounds(27, 78, 81, 25);
		lblCargo.setText("Cargo");

		textCargoEmpleado = new Text(grpDetalles, SWT.BORDER);
		textCargoEmpleado.setBounds(114, 75, 227, 31);

		Label lblCodigo = new Label(grpDetalles, SWT.NONE);
		lblCodigo.setBounds(27, 123, 81, 25);
		lblCodigo.setText("Codigo");

		textCodigoEmpleado = new Text(grpDetalles, SWT.BORDER);
		textCodigoEmpleado.setBounds(114, 120, 227, 31);

		Label lblSalario = new Label(grpDetalles, SWT.NONE);
		lblSalario.setBounds(404, 32, 81, 25);
		lblSalario.setText("Salario");

		textSalarioEmpleado = new Text(grpDetalles, SWT.BORDER);
		textSalarioEmpleado.setBounds(491, 29, 234, 31);

		Label lblDireccin = new Label(grpDetalles, SWT.NONE);
		lblDireccin.setBounds(404, 78, 81, 25);
		lblDireccin.setText("Direcci\u00F3n");
		
		text_busqueda = new Text(grpAcciones, SWT.BORDER);
		text_busqueda.setBounds(111, 38, 223, 31);

		text_busqueda.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				busquedad = source.getText();
				tableViewerEmpleado.refresh();
			}
		});
		text_busqueda.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					Text text = (Text) e.getSource();
					text.setText(""); //
				}
			}
		});

		tableViewerEmpleado.addFilter(new ViewerFilter() { // Nullpointed acá

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {

				Empleado empleadoBusqueda = (Empleado) element;
				try {
					return

					empleadoBusqueda.getNombreEmpleado().contains(busquedad)
							|| empleadoBusqueda.getNombreEmpleado().toLowerCase().contains(busquedad.toLowerCase())
							|| empleadoBusqueda.getNombreEmpleado().toUpperCase().contains(busquedad.toUpperCase()) ||

							empleadoBusqueda.getCargo().contains(busquedad.toUpperCase())
							|| empleadoBusqueda.getCargo().toLowerCase().contains(busquedad.toLowerCase())
							|| empleadoBusqueda.getCargo().toUpperCase().contains(busquedad.toUpperCase()) ||

							empleadoBusqueda.getDireccion().contains(busquedad)
							|| empleadoBusqueda.getDireccion().toLowerCase().contains(busquedad.toLowerCase())
							|| empleadoBusqueda.getDireccion().toUpperCase().contains(busquedad.toUpperCase());

				} catch (Exception e) {

					return false;
				}
			}
		});

		textDireccionEmpleado = new Text(grpDetalles, SWT.BORDER);
		textDireccionEmpleado.setBounds(491, 75, 234, 31);

		Button btnAgregarEmpleado = new Button(grpDetalles, SWT.NONE);
		btnAgregarEmpleado.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (verificarCamposVacios() == true) {

					Empleado empleadoNuevo = new Empleado();

					try {
						empleadoNuevo.setNombreEmpleado(textNombreEmpleado.getText());
						empleadoNuevo.setCargo(textCargoEmpleado.getText());
						empleadoNuevo.setCodigoEmpleado(Integer.parseInt(textCodigoEmpleado.getText()));
						empleadoNuevo.setDireccion(textDireccionEmpleado.getText());
						empleadoNuevo.setSalario(Double.valueOf(textSalarioEmpleado.getText()));

						crudEmpleadoViewController.crearEmpleado(empleadoNuevo);
						initDataBindings();
						limpiarCampoTexto();
						crudEmpleadoViewController.salvarDatos();
						crudEmpleadoViewController.guardaTextoPlano();
						crudEmpleadoViewController.guardarArchivoLog("Se ha creado el empleado: "+ empleadoNuevo.getNombreEmpleado(),1,"NuevoEmpleado");
					} catch (yaExiste e1) {
						JOptionPane.showMessageDialog(null, "El empleado ya existe", null, JOptionPane.WARNING_MESSAGE,
								null);
						crudEmpleadoViewController.guardarArchivoLog("El empleado ya existe", 1, "YaExisteEmpleado");
					} catch (numeroErroneo e2) {
						JOptionPane.showMessageDialog(null, "No se aceptan letras en código ó salario", null,
								JOptionPane.WARNING_MESSAGE, null);
						crudEmpleadoViewController.guardarArchivoLog("Datos Introducidos erroneos", 1, "DatosErroneos");
					}

				}

			}
		});
		btnAgregarEmpleado.setBounds(441, 144, 161, 35);
		btnAgregarEmpleado.setText("Agregar Empleado");

		Button btnActualizarEmpleado = new Button(grpDetalles, SWT.NONE);
		btnActualizarEmpleado.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
	
				if (empleadoSeleccionado != null) {
					try {
						crudEmpleadoViewController.actualizarEmpleado(textNombreEmpleado.getText(),
								textCargoEmpleado.getText(), Integer.valueOf(textCodigoEmpleado.getText()),
								textDireccionEmpleado.getText(), Double.valueOf((textSalarioEmpleado.getText())), codigoAnterior);
						        crudEmpleadoViewController.salvarDatos();
						        crudEmpleadoViewController.guardaTextoPlano();
						        crudEmpleadoViewController.guardarArchivoLog("Se hizo una modificación en el empleado: "+ empleadoSeleccionado.getNombreEmpleado()
						, 1, "ModificaciónEmpleado");
					} catch (numeroErroneo e1) {
						JOptionPane.showMessageDialog(null, "No se aceptan letras en código ó salario", null,
								JOptionPane.WARNING_MESSAGE, null);
						crudEmpleadoViewController.guardarArchivoLog("Datos Introducidos erroneos", 1, "DatosErroneos");
					} catch(yaExiste e2){
						JOptionPane.showMessageDialog(null, "El empleado ya existe", null, JOptionPane.WARNING_MESSAGE,
								null);
						crudEmpleadoViewController.guardarArchivoLog("El empleado ya existe", 1, "YaExisteEmpleado");
					}
					initDataBindings();
					
				}
			}
		});
		btnActualizarEmpleado.setBounds(645, 144, 173, 35);
		btnActualizarEmpleado.setText("Actualizar Empleado");
		dataBindingContext = initDataBindings();
		// TODO Auto-generated constructor stub

	}

	public void limpiarCampoTexto() {
		textNombreEmpleado.setText("");
		textCargoEmpleado.setText("");
		textCodigoEmpleado.setText("");
		textDireccionEmpleado.setText("");
		textSalarioEmpleado.setText("");
	}

	public Boolean verificarCamposVacios() {

		if (textCargoEmpleado.getText().equalsIgnoreCase("") || textCodigoEmpleado.getText().equalsIgnoreCase("")
				|| textDireccionEmpleado.getText().equalsIgnoreCase("")
				|| textNombreEmpleado.getText().equalsIgnoreCase("")
				|| textSalarioEmpleado.getText().equalsIgnoreCase("")) {

			return false;
		} else {

			return true;
		}
	}
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Empleado.class, new String[]{"nombreEmpleado", "cargo", "codigoEmpleado", "salario", "direccion"});
		tableViewerEmpleado.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewerEmpleado.setContentProvider(listContentProvider);
		//
		IObservableList listaEmpleadosFerreteriaObserveList = PojoProperties.list("listaEmpleados").observe(ferreteria);
		tableViewerEmpleado.setInput(listaEmpleadosFerreteriaObserveList);
		//
		return bindingContext;
	}
}
