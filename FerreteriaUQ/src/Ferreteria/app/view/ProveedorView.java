package Ferreteria.app.view;

import javax.swing.JOptionPane;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;

import Ferreteria.app.Excepciones.numeroErroneo;
import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.controller.CrudProveedorViewController;
import Ferreteria.app.controller.ModelFactoryController;
import Ferreteria.app.model.Empleado;
import Ferreteria.app.model.Ferreteria;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import Ferreteria.app.model.Proveedor;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.swt.widgets.Text;

public class ProveedorView extends Composite {

	CrudProveedorViewController crudProveedorViewController = new CrudProveedorViewController();
	Ferreteria ferreteria = crudProveedorViewController.getFerreteria();
	ModelFactoryController model = new ModelFactoryController();

	private DataBindingContext dataBindingContext = null;
	private Table table;
	private TableViewer tableViewer;
	private Text textNombre;
	private Text textCodigo;
	private Text textDireccion;
	private Text textTelefono;
	Proveedor proveedorSeleccionado;
	public String busquedad = "";
	private Text text_busqueda;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ProveedorView(Composite parent, int style) {
		super(parent, style);
		Group grpAcciones = new Group(this, SWT.NONE);
		grpAcciones.setText("Acciones");
		grpAcciones.setBounds(10, 10, 918, 92);
		
		Label lblBusqueda = new Label(grpAcciones, SWT.NONE);
		lblBusqueda.setBounds(25, 40, 81, 25);
		lblBusqueda.setText("Busqueda");
		
		Button btnNuevoProveedor = new Button(grpAcciones, SWT.NONE);
		btnNuevoProveedor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				limpiarCampoTexto();
			}
		});
		btnNuevoProveedor.setBounds(497, 40, 180, 35);
		btnNuevoProveedor.setText("Nuevo Proveedor");
		
		Button btnEliminarProveedor = new Button(grpAcciones, SWT.NONE);
		btnEliminarProveedor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				Boolean flag = false;
				if(proveedorSeleccionado != null){
					flag= crudProveedorViewController.eliminarProveedor(proveedorSeleccionado.getCodigoProveedor());
					limpiarCampoTexto();
					initDataBindings();
					 
					crudProveedorViewController.salvarDatos();
					crudProveedorViewController.guardaDatosTextoPlano();
						crudProveedorViewController.guardarArchivoLog("Se ha eliminado el proveedor: "+ proveedorSeleccionado.getNombreProveedor(),
								2, "ProveedorEliminado");
					
					if(flag == true){
						JOptionPane.showMessageDialog(null, "El proveerdor se elimino con exito");
					}else{
						JOptionPane.showMessageDialog(null, "No se pudo eliminar");
						crudProveedorViewController.guardarArchivoLog("Falló al eliminar", 1, "FalloEliminar");
					}
				}
			}
		});
		
		btnEliminarProveedor.setBounds(702, 40, 180, 35);
		btnEliminarProveedor.setText("Eliminar Proveedor");
		
		Group grpListaProveedores = new Group(this, SWT.NONE);
		grpListaProveedores.setText("Lista Proveedores");
		grpListaProveedores.setBounds(20, 108, 908, 256);
		
		tableViewer = new TableViewer(grpListaProveedores, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				
				proveedorSeleccionado= (Proveedor)table.getItem(table.getSelectionIndex()).getData();
				
				textCodigo.setText(Integer.toString(proveedorSeleccionado.getCodigoProveedor()));
				textDireccion.setText(proveedorSeleccionado.getDireccionProveedor());
				textNombre.setText(proveedorSeleccionado.getNombreProveedor());
				textTelefono.setText(Integer.toString(proveedorSeleccionado.getTelefonoProveedor()));
			}
		});
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(10, 29, 888, 217);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(198);
		tblclmnNombre.setText("Nombre");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCdigo = tableViewerColumn_1.getColumn();
		tblclmnCdigo.setWidth(197);
		tblclmnCdigo.setText("C\u00F3digo");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnTelefono = tableViewerColumn_2.getColumn();
		tblclmnTelefono.setWidth(159);
		tblclmnTelefono.setText("Telefono");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnDireccin = tableViewerColumn_3.getColumn();
		tblclmnDireccin.setWidth(330);
		tblclmnDireccin.setText("Direcci\u00F3n");
		
		Label lblNombre = new Label(this, SWT.NONE);
		lblNombre.setBounds(44, 382, 71, 25);
		lblNombre.setText("Nombre");
		
		Label lblCdigo = new Label(this, SWT.NONE);
		lblCdigo.setBounds(44, 446, 81, 25);
		lblCdigo.setText("C\u00F3digo");
		
		Label lblDireccin = new Label(this, SWT.NONE);
		lblDireccin.setBounds(445, 382, 81, 25);
		lblDireccin.setText("Direcci\u00F3n");
		
		Label lblTelefono = new Label(this, SWT.NONE);
		lblTelefono.setBounds(44, 507, 81, 25);
		lblTelefono.setText("Telefono");
		
		textNombre = new Text(this, SWT.BORDER);
		textNombre.setBounds(131, 379, 202, 31);
		
		textCodigo = new Text(this, SWT.BORDER);
		textCodigo.setBounds(131, 443, 202, 31);
		
		textDireccion = new Text(this, SWT.BORDER);
		textDireccion.setBounds(544, 379, 202, 31);
		
		textTelefono = new Text(this, SWT.BORDER);
		textTelefono.setBounds(131, 504, 202, 31);
		
		Button btnAgregarProveedor = new Button(this, SWT.NONE);
		btnAgregarProveedor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
			
				if(verificarCamposVacios()== true){
					 Proveedor prooNuevo = new Proveedor();
					 
					 
					 try{
						 prooNuevo.setNombreProveedor(textNombre.getText());
						 prooNuevo.setCodigoProveedor(Integer.parseInt(textCodigo.getText()));
						 prooNuevo.setTelefonoProveedor(Integer.parseInt(textTelefono.getText()));
						 prooNuevo.setDireccionProveedor(textDireccion.getText());
						 
						 crudProveedorViewController.crearProveedor(prooNuevo);
						 initDataBindings();
						 limpiarCampoTexto();
						 
						 crudProveedorViewController.salvarDatos();
						 crudProveedorViewController.guardaDatosTextoPlano();
							crudProveedorViewController.guardarArchivoLog("Se ha añadido el proveedor: "+prooNuevo.getNombreProveedor(),
									2, "ProveedorAñadido");
					 } catch(yaExiste e1){
						 JOptionPane.showMessageDialog(null, "El proveedor ya existe" , null, JOptionPane.WARNING_MESSAGE, null);
						 crudProveedorViewController.guardarArchivoLog("El proveedor ya existe", 1, "ProveedorRepetido");
						 
					 }catch(numeroErroneo e2){
						 JOptionPane.showMessageDialog(null, "No se aceptan letras en código y telefono", null, JOptionPane.WARNING_MESSAGE, null);
						 crudProveedorViewController.guardarArchivoLog("Datos erroneos", 1, "DatosErroneos");
					 }
				}
			}
		});
		btnAgregarProveedor.setBounds(474, 497, 202, 35);
		btnAgregarProveedor.setText("Agregar Proveedor");
		
		Button btnModificarProveedor = new Button(this, SWT.NONE);
		btnModificarProveedor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(proveedorSeleccionado != null){
					crudProveedorViewController.actualizarProveedor(textNombre.getText(), 
							Integer.valueOf(textCodigo.getText()), Integer.valueOf(textTelefono.getText()),
							textDireccion.getText());
					limpiarCampoTexto();
					initDataBindings();
					 crudProveedorViewController.salvarDatos();
					 crudProveedorViewController.guardaDatosTextoPlano();
						crudProveedorViewController.guardarArchivoLog("Se ha modificado el proveedor: "+proveedorSeleccionado.getNombreProveedor(),
								2, "ProveedorModificado");
				}
			}
		});
		btnModificarProveedor.setBounds(693, 497, 202, 35);
		btnModificarProveedor.setText("Modificar Proveedor");
		text_busqueda = new Text(grpAcciones, SWT.BORDER);
		text_busqueda.setBounds(111, 38, 223, 31);

		text_busqueda.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				busquedad = source.getText();
				tableViewer.refresh();
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

		tableViewer.addFilter(new ViewerFilter() { // Nullpointed acá

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {

				Proveedor proveedorBusqueda = (Proveedor) element;
				try {
					return

					proveedorBusqueda.getNombreProveedor().contains(busquedad)
							|| proveedorBusqueda.getNombreProveedor().toLowerCase().contains(busquedad.toLowerCase())
							|| proveedorBusqueda.getNombreProveedor().toUpperCase().contains(busquedad.toUpperCase()) ||

							proveedorBusqueda.getDireccionProveedor().contains(busquedad.toUpperCase())
							|| proveedorBusqueda.getDireccionProveedor().toLowerCase().contains(busquedad.toLowerCase())
							||  proveedorBusqueda.getDireccionProveedor().toUpperCase().contains(busquedad.toUpperCase()) ||
							String.valueOf(proveedorBusqueda.getCodigoProveedor()).contains(busquedad);
							
				} catch (Exception e) {

					return false;
				}
			}
		});
		dataBindingContext = initDataBindings();

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(),
				Proveedor.class,
				new String[] { "nombreProveedor", "codigoProveedor", "telefonoProveedor", "direccionProveedor" });
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList listaProveedorFerreteriaObserveList = PojoProperties.list("listaProveedor").observe(ferreteria);
		tableViewer.setInput(listaProveedorFerreteriaObserveList);
		//
		return bindingContext;
	}

	public void limpiarCampoTexto() {
		textCodigo.setText("");
		textDireccion.setText("");
		textNombre.setText("");
		textTelefono.setText("");
	}

	public Boolean verificarCamposVacios() {
		if (textCodigo.getText().equalsIgnoreCase("") || textDireccion.getText().equalsIgnoreCase("")
				|| textNombre.getText().equalsIgnoreCase("") || textTelefono.getText().equalsIgnoreCase("")) {

			return false;
		} else {
			return true;
		}
	}
}
