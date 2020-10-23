package Ferreteria.app.view;

import javax.swing.JOptionPane;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;

import Ferreteria.app.Excepciones.cantidadInvalida;
import Ferreteria.app.Excepciones.nadaSeleccionado;
import Ferreteria.app.Excepciones.soloUnProveedor;
import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.controller.CrudCompraViewController;
import Ferreteria.app.controller.ModelFactoryController;
import Ferreteria.app.model.Ferreteria;
import Ferreteria.app.model.Producto;
import Ferreteria.app.model.Proveedor;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import Ferreteria.app.model.Compra;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;
import Ferreteria.app.model.Empleado;
import Ferreteria.app.model.Factura_Compra;
import Ferreteria.app.model.Detalle_Inventario;

public class CompraView extends Composite {

	CrudCompraViewController crudCompraViewController = new CrudCompraViewController();
	Ferreteria ferreteria = crudCompraViewController.getFerreteria();
	Proveedor proveedor = crudCompraViewController.getFerreteria().getProveedor();
	ModelFactoryController model = new ModelFactoryController();

	private DataBindingContext dataBindingContext = null;
	private Text textNumeroCompra;
	private Text textFechaCompra;
	private Text textValorDeLaCompra;
	private Table tableDisponibleProveedor;
	private Table tableProductoCompra;
	private Table tableCompraRealizada;
	private TableViewer tableViewerComprasHechas;

	private Producto productoSeleccionado;
	private Detalle_Inventario detalleSeleccionado, detalleaux;
	private Empleado empleadoSeleccionadoCombo;
	private Proveedor proveedoraux;
	private TableViewer tableViewerProductosEnlaCompra;
	private TableViewer tableViewerProDisponibles;
	private ComboViewer comboViewer_1;
	private Text textBusquedaProducto;
	private String busquedad = "";
	private int cantidad, cantidadtotal;
	private double valor;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CompraView(Composite parent, int style) {
		super(parent, style);
		Group grpInformacinDeLa = new Group(this, SWT.NONE);
		grpInformacinDeLa.setText("Informaci\u00F3n de la compra");
		grpInformacinDeLa.setBounds(10, 10, 918, 369);

		Label lblNmeroDeCompra = new Label(grpInformacinDeLa, SWT.NONE);
		lblNmeroDeCompra.setBounds(10, 31, 156, 25);
		lblNmeroDeCompra.setText("N\u00FAmero de compra");

		Label lblFechaDeCompra = new Label(grpInformacinDeLa, SWT.NONE);
		lblFechaDeCompra.setBounds(10, 62, 156, 25);
		lblFechaDeCompra.setText("Fecha de compra");

		Label lblValorDeCompra = new Label(grpInformacinDeLa, SWT.NONE);
		lblValorDeCompra.setBounds(420, 31, 137, 25);
		lblValorDeCompra.setText("Valor de compra");

		textNumeroCompra = new Text(grpInformacinDeLa, SWT.BORDER);
		textNumeroCompra.setBounds(175, 28, 221, 31);

		textFechaCompra = new Text(grpInformacinDeLa, SWT.BORDER);
		textFechaCompra.setToolTipText("");
		textFechaCompra.setBounds(175, 62, 221, 31);

		textValorDeLaCompra = new Text(grpInformacinDeLa, SWT.BORDER);
		textValorDeLaCompra.setEnabled(false);
		textValorDeLaCompra.setEditable(false);
		textValorDeLaCompra.setBounds(563, 28, 221, 31);

		Group grpProductosDisponibles = new Group(grpInformacinDeLa, SWT.NONE);
		grpProductosDisponibles.setText("Productos Disponibles");
		grpProductosDisponibles.setBounds(10, 94, 429, 76);

		Label lblBusqueda = new Label(grpProductosDisponibles, SWT.NONE);
		lblBusqueda.setBounds(10, 41, 81, 25);
		lblBusqueda.setText("Busqueda");

		Group grpInformacinDelEmpleado = new Group(grpInformacinDeLa, SWT.NONE);
		grpInformacinDelEmpleado.setText("Informaci\u00F3n del empleado");
		grpInformacinDelEmpleado.setBounds(463, 94, 445, 76);

		comboViewer_1 = new ComboViewer(grpInformacinDelEmpleado, SWT.READ_ONLY);
		Combo comboInfoEmp = comboViewer_1.getCombo();
		comboInfoEmp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection x = (IStructuredSelection) comboViewer_1.getSelection();
				empleadoSeleccionadoCombo = (Empleado) x.getFirstElement();
			}
		});
		comboInfoEmp.setBounds(10, 33, 425, 33);

		Group grpDisponiblesProveedor = new Group(grpInformacinDeLa, SWT.NONE);
		grpDisponiblesProveedor.setText("Disponibles Proveedor");
		grpDisponiblesProveedor.setBounds(10, 176, 429, 190);

		tableViewerProDisponibles = new TableViewer(grpDisponiblesProveedor, SWT.BORDER | SWT.FULL_SELECTION);
		tableDisponibleProveedor = tableViewerProDisponibles.getTable();
		tableDisponibleProveedor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				productoSeleccionado = (Producto) tableDisponibleProveedor
						.getItem(tableDisponibleProveedor.getSelectionIndex()).getData();
			}
		});
		tableDisponibleProveedor.setLinesVisible(true);
		tableDisponibleProveedor.setHeaderVisible(true);
		tableDisponibleProveedor.setBounds(10, 23, 409, 157);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewerProDisponibles, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(185);
		tblclmnNombre.setText("Nombre");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewerProDisponibles, SWT.NONE);
		TableColumn tblclmnCodigo = tableViewerColumn_1.getColumn();
		tblclmnCodigo.setWidth(97);
		tblclmnCodigo.setText("Codigo");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewerProDisponibles, SWT.NONE);
		TableColumn tblclmnProveedor_1 = tableViewerColumn_2.getColumn();
		tblclmnProveedor_1.setWidth(124);
		tblclmnProveedor_1.setText("Proveedor");

		Group grpObjetosAComprar = new Group(grpInformacinDeLa, SWT.NONE);
		grpObjetosAComprar.setText("Objetos a comprar");
		grpObjetosAComprar.setBounds(447, 176, 461, 190);

		tableViewerProductosEnlaCompra = new TableViewer(grpObjetosAComprar, SWT.BORDER | SWT.FULL_SELECTION);
		tableProductoCompra = tableViewerProductosEnlaCompra.getTable();
		tableProductoCompra.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				detalleSeleccionado = (Detalle_Inventario) tableProductoCompra
						.getItem(tableProductoCompra.getSelectionIndex()).getData();
			}
		});
		tableProductoCompra.setLinesVisible(true);
		tableProductoCompra.setHeaderVisible(true);
		tableProductoCompra.setBounds(10, 23, 441, 157);

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewerProductosEnlaCompra, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn_3.getColumn();
		tableColumn.setWidth(132);
		tableColumn.setText("Nombre");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewerProductosEnlaCompra, SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_4.getColumn();
		tableColumn_1.setWidth(93);
		tableColumn_1.setText("Codigo");

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewerProductosEnlaCompra, SWT.NONE);
		TableColumn tblclmnProveedor_2 = tableViewerColumn_5.getColumn();
		tblclmnProveedor_2.setWidth(126);
		tblclmnProveedor_2.setText("Proveedor");

		TableViewerColumn tableViewerColumn_11 = new TableViewerColumn(tableViewerProductosEnlaCompra, SWT.NONE);
		TableColumn tblclmnCantidad = tableViewerColumn_11.getColumn();
		tblclmnCantidad.setWidth(87);
		tblclmnCantidad.setText("Cantidad");

		Group grpListaDeCompras = new Group(this, SWT.NONE);
		grpListaDeCompras.setText("Lista de compras realizadas");
		grpListaDeCompras.setBounds(10, 426, 918, 159);

		tableViewerComprasHechas = new TableViewer(grpListaDeCompras, SWT.BORDER | SWT.FULL_SELECTION);
		tableCompraRealizada = tableViewerComprasHechas.getTable();
		tableCompraRealizada.setLinesVisible(true);
		tableCompraRealizada.setHeaderVisible(true);
		tableCompraRealizada.setBounds(10, 30, 898, 120);

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(tableViewerComprasHechas, SWT.NONE);
		TableColumn tblclmnCodigoDeLa = tableViewerColumn_6.getColumn();
		tblclmnCodigoDeLa.setWidth(187);
		tblclmnCodigoDeLa.setText("Codigo de la compra");

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewerComprasHechas, SWT.NONE);
		TableColumn tblclmnFecha = tableViewerColumn_7.getColumn();
		tblclmnFecha.setWidth(184);
		tblclmnFecha.setText("Fecha");

		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(tableViewerComprasHechas, SWT.NONE);
		TableColumn tblclmnCantidadComprada = tableViewerColumn_8.getColumn();
		tblclmnCantidadComprada.setWidth(218);
		tblclmnCantidadComprada.setText("Cantidad Comprada");

		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tableViewerComprasHechas, SWT.NONE);
		TableColumn tblclmnValor = tableViewerColumn_9.getColumn();
		tblclmnValor.setWidth(134);
		tblclmnValor.setText("Valor");

		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(tableViewerComprasHechas, SWT.NONE);
		TableColumn tblclmnProveedor = tableViewerColumn_10.getColumn();
		tblclmnProveedor.setWidth(168);
		tblclmnProveedor.setText("Proveedor");

		Button btnActualizar = new Button(this, SWT.NONE);
		btnActualizar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				initDataBindings();
			}
		});
		btnActualizar.setBounds(522, 385, 105, 35);
		btnActualizar.setText("Actualizar");

		textBusquedaProducto = new Text(grpProductosDisponibles, SWT.BORDER);
		textBusquedaProducto.setBounds(118, 38, 301, 31);
		textBusquedaProducto.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				busquedad = source.getText();
				tableViewerProDisponibles.refresh();
			}
		});
		textBusquedaProducto.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					Text text = (Text) e.getSource();
					text.setText(""); //
				}
			}
		});
		tableViewerProDisponibles.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {

				Producto productoBusqueda = (Producto) element;
				try {
					return productoBusqueda.getNombreProducto().contains(busquedad)
							|| productoBusqueda.getNombreProducto().toLowerCase().contains(busquedad)
							|| productoBusqueda.getNombreProducto().toUpperCase().contains(busquedad)
							|| String.valueOf(productoBusqueda.getCodigoProducto()).contains((busquedad))
							|| productoBusqueda.getProveedorAsociado().getNombreProveedor().contains(busquedad)
							|| productoBusqueda.getProveedorAsociado().getNombreProveedor().toLowerCase().contains(busquedad)
							|| productoBusqueda.getProveedorAsociado().getNombreProveedor().toUpperCase().contains(busquedad);

				} catch (Exception e) {

					return false;
				}
			}
		});

		Button btnNuevo = new Button(this, SWT.NONE);
		btnNuevo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				limpiarCampos();
				initDataBindings();
			}
		});
		btnNuevo.setBounds(668, 385, 105, 35);
		btnNuevo.setText("Nuevo");

		Button btnAgregar = new Button(this, SWT.NONE);
		btnAgregar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (verificarCamposVacios() == true) {
					try {

						Factura_Compra factura_compra = new Factura_Compra(Integer.parseInt(textNumeroCompra.getText()),
								cantidadtotal, valor);
						proveedoraux = crudCompraViewController.getModelFactoryController().getFerreteria()
								.getListaObjetosAcomprar().get(0).getProducto().getProveedorAsociado();

						Compra compra = new Compra(Integer.parseInt(textNumeroCompra.getText()),
								textFechaCompra.getText(), cantidadtotal, factura_compra, proveedoraux,
								empleadoSeleccionadoCombo);

						compra.setCantidadCompra(cantidadtotal);
						compra.setCodigoCompra(Integer.parseInt(textNumeroCompra.getText()));
						compra.setEmpleadoAsociado(empleadoSeleccionadoCombo);
						compra.setFechaCompra(textFechaCompra.getText());
						compra.setProveedorAsociado(proveedoraux);
						compra.setFactura_Compra(factura_compra);

						crudCompraViewController.crearCompra(compra);
					} catch (yaExiste e1) {
						JOptionPane.showMessageDialog(null, "El código de la compra ya existe");;
					} catch (NumberFormatException e2) {
						System.out.println(e2);
					}
					limpiarCampos();
					initDataBindings();
				}else{
					JOptionPane.showMessageDialog(null, "Debe llenar todos los campos y seleccionar un empleado");
				}
			}
		});
		btnAgregar.setBounds(800, 385, 105, 35);
		btnAgregar.setText("Agregar");

		Button buttonAdicionar = new Button(this, SWT.NONE);
		buttonAdicionar.setBounds(262, 385, 105, 35);
		buttonAdicionar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (productoSeleccionado != null) {
					cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuál es la cantidad a comprar?: "));
					if (cantidad > 0) {
						try {
							crudCompraViewController.anadirDetalleALista(productoSeleccionado, cantidad);
						} catch (soloUnProveedor e1) {
							JOptionPane.showMessageDialog(null, "Solo se permiten un proveedor");
						}
						valor += productoSeleccionado.getPrecio() * cantidad;
						cantidadtotal += cantidad;
						textValorDeLaCompra.setText("$" + String.valueOf(valor));
						initDataBindings();
					} else {
						try {
							throw new cantidadInvalida("Cantidad invalida");
						} catch (cantidadInvalida e1) {
							JOptionPane.showMessageDialog(null, "Cantidad invalida");
						}
					}
				} else {
					try {
						throw new nadaSeleccionado("Debe seleccionar un producto");
					} catch (nadaSeleccionado e1) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar un producto");
					}
				}
			}
		});
		buttonAdicionar.setText(">>");

		Button buttonQuitar = new Button(this, SWT.NONE);
		buttonQuitar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (detalleSeleccionado != null) {
					crudCompraViewController.quitarDetalleLista(detalleSeleccionado);
					valor -= detalleSeleccionado.getProducto().getPrecio() * detalleSeleccionado.getCantidadProducto();
					textValorDeLaCompra.setText("$" + String.valueOf(valor));
					cantidadtotal -= detalleSeleccionado.getCantidadProducto();
					initDataBindings();
				} else {
					try {
						throw new nadaSeleccionado("Debe seleccionar un producto");
					} catch (nadaSeleccionado e1) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar un producto");
					}
				}
			}
		});
		buttonQuitar.setBounds(397, 385, 105, 35);
		buttonQuitar.setText("<<");
		dataBindingContext = initDataBindings();

	}

	public Boolean verificarCamposVacios() {
		if (textFechaCompra.getText().equalsIgnoreCase("") || textNumeroCompra.getText().equalsIgnoreCase("")
				|| textValorDeLaCompra.getText().equalsIgnoreCase("") || empleadoSeleccionadoCombo == null) {
			return false;
		} else {
			return true;
		}
	}
	public void limpiarCampos(){
		textFechaCompra.setText("");
		textNumeroCompra.setText("");
		textValorDeLaCompra.setText("");
		empleadoSeleccionadoCombo=null;
		textBusquedaProducto.setText("");
		crudCompraViewController.limpiarLista();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Compra.class,
				new String[] { "codigoCompra", "fechaCompra", "cantidadCompra", "factura_Compra.totalCompra",
						"proveedorAsociado.nombreProveedor" });
		tableViewerComprasHechas.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewerComprasHechas.setContentProvider(listContentProvider);
		//
		IObservableList listaComprasFerreteriaObserveList = PojoProperties.list("listaCompras").observe(ferreteria);
		tableViewerComprasHechas.setInput(listaComprasFerreteriaObserveList);
		//
		ObservableListContentProvider listContentProvider_4 = new ObservableListContentProvider();
		IObservableMap observeMap_1 = PojoObservables.observeMap(listContentProvider_4.getKnownElements(),
				Empleado.class, "nombreEmpleado");
		comboViewer_1.setLabelProvider(new ObservableMapLabelProvider(observeMap_1));
		comboViewer_1.setContentProvider(listContentProvider_4);
		//
		IObservableList listaEmpleadosFerreteriaObserveList = PojoProperties.list("listaEmpleados").observe(ferreteria);
		comboViewer_1.setInput(listaEmpleadosFerreteriaObserveList);
		//
		ObservableListContentProvider listContentProvider_2 = new ObservableListContentProvider();
		IObservableMap[] observeMaps_2 = PojoObservables.observeMaps(listContentProvider_2.getKnownElements(),
				Producto.class,
				new String[] { "nombreProducto", "codigoProducto", "proveedorAsociado.nombreProveedor" });
		tableViewerProDisponibles.setLabelProvider(new ObservableMapLabelProvider(observeMaps_2));
		tableViewerProDisponibles.setContentProvider(listContentProvider_2);
		//
		IObservableList listaProductosFerreteriaObserveList = PojoProperties.list("listaProductos").observe(ferreteria);
		tableViewerProDisponibles.setInput(listaProductosFerreteriaObserveList);
		//
		ObservableListContentProvider listContentProvider_1 = new ObservableListContentProvider();
		IObservableMap[] observeMaps_1 = PojoObservables.observeMaps(listContentProvider_1.getKnownElements(),
				Detalle_Inventario.class, new String[] { "producto.nombreProducto", "producto.codigoProducto",
						"producto.proveedorAsociado.nombreProveedor", "cantidadProducto" });
		tableViewerProductosEnlaCompra.setLabelProvider(new ObservableMapLabelProvider(observeMaps_1));
		tableViewerProductosEnlaCompra.setContentProvider(listContentProvider_1);
		//
		IObservableList listaObjetosAcomprarFerreteriaObserveList = PojoProperties.list("listaObjetosAcomprar")
				.observe(ferreteria);
		tableViewerProductosEnlaCompra.setInput(listaObjetosAcomprarFerreteriaObserveList);
		//
		return bindingContext;
	}
}
