package Ferreteria.app.view;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;

import Ferreteria.app.controller.CrudCompraViewController;
import Ferreteria.app.controller.ModelFactoryController;
import Ferreteria.app.model.Ferreteria;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import Ferreteria.app.model.Compra;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;

public class CompraView extends Composite {
	
	CrudCompraViewController crudCompraViewController= new CrudCompraViewController();
	Ferreteria ferreteria = crudCompraViewController.getFerreteria();
	ModelFactoryController model= new ModelFactoryController();
	
	private DataBindingContext dataBindingContext= null;
	private Text textNumeroCompra;
	private Text textFechaCompra;
	private Text textValorDeLaCompra;
	private Table table;
	private Table table_1;
	private Table table_2;
	private TableViewer tableViewer_2;

	/**
	 * Create the composite.
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
		
		Group grpInformacinDelProveedor = new Group(grpInformacinDeLa, SWT.NONE);
		grpInformacinDelProveedor.setText("Informaci\u00F3n del proveedor");
		grpInformacinDelProveedor.setBounds(10, 94, 364, 76);
		
		ComboViewer comboViewer = new ComboViewer(grpInformacinDelProveedor, SWT.READ_ONLY);
		Combo comboInfoProv = comboViewer.getCombo();
		comboInfoProv.setBounds(10, 33, 342, 33);
		
		Group grpInformacinDelEmpleado = new Group(grpInformacinDeLa, SWT.NONE);
		grpInformacinDelEmpleado.setText("Informaci\u00F3n del empleado");
		grpInformacinDelEmpleado.setBounds(544, 94, 364, 76);
		
		ComboViewer comboViewer_1 = new ComboViewer(grpInformacinDelEmpleado, SWT.READ_ONLY);
		Combo comboInfoEmp = comboViewer_1.getCombo();
		comboInfoEmp.setBounds(10, 33, 344, 33);
		
		Group grpDisponiblesProveedor = new Group(grpInformacinDeLa, SWT.NONE);
		grpDisponiblesProveedor.setText("Disponibles Proveedor");
		grpDisponiblesProveedor.setBounds(10, 176, 364, 190);
		
		TableViewer tableViewer = new TableViewer(grpDisponiblesProveedor, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(10, 23, 344, 157);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(152);
		tblclmnNombre.setText("Nombre");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCodigo = tableViewerColumn_1.getColumn();
		tblclmnCodigo.setWidth(100);
		tblclmnCodigo.setText("Codigo");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCantidad = tableViewerColumn_2.getColumn();
		tblclmnCantidad.setWidth(87);
		tblclmnCantidad.setText("Cantidad");
		
		Group grpObjetosAComprar = new Group(grpInformacinDeLa, SWT.NONE);
		grpObjetosAComprar.setText("Objetos a comprar");
		grpObjetosAComprar.setBounds(544, 176, 364, 190);
		
		TableViewer tableViewer_1 = new TableViewer(grpObjetosAComprar, SWT.BORDER | SWT.FULL_SELECTION);
		table_1 = tableViewer_1.getTable();
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		table_1.setBounds(10, 23, 344, 157);
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn_3.getColumn();
		tableColumn.setWidth(152);
		tableColumn.setText("Nombre");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_4.getColumn();
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("Codigo");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tableColumn_2 = tableViewerColumn_5.getColumn();
		tableColumn_2.setWidth(87);
		tableColumn_2.setText("Cantidad");
		
		Button buttonAdicionar = new Button(grpInformacinDeLa, SWT.NONE);
		buttonAdicionar.setBounds(402, 234, 105, 35);
		buttonAdicionar.setText(">>");
		
		Button buttonQuitar = new Button(grpInformacinDeLa, SWT.NONE);
		buttonQuitar.setBounds(402, 296, 105, 35);
		buttonQuitar.setText("<<");
		
		Group grpListaDeCompras = new Group(this, SWT.NONE);
		grpListaDeCompras.setText("Lista de compras realizadas");
		grpListaDeCompras.setBounds(10, 426, 918, 159);
		
		tableViewer_2 = new TableViewer(grpListaDeCompras, SWT.BORDER | SWT.FULL_SELECTION);
		table_2 = tableViewer_2.getTable();
		table_2.setLinesVisible(true);
		table_2.setHeaderVisible(true);
		table_2.setBounds(10, 30, 898, 120);
		
		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnCodigoDeLa = tableViewerColumn_6.getColumn();
		tblclmnCodigoDeLa.setWidth(187);
		tblclmnCodigoDeLa.setText("Codigo de la compra");
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnFecha = tableViewerColumn_7.getColumn();
		tblclmnFecha.setWidth(184);
		tblclmnFecha.setText("Fecha");
		
		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnCantidadComprada = tableViewerColumn_8.getColumn();
		tblclmnCantidadComprada.setWidth(218);
		tblclmnCantidadComprada.setText("Cantidad Comprada");
		
		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnValor = tableViewerColumn_9.getColumn();
		tblclmnValor.setWidth(134);
		tblclmnValor.setText("Valor");
		
		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(tableViewer_2, SWT.NONE);
		TableColumn tblclmnProveedor = tableViewerColumn_10.getColumn();
		tblclmnProveedor.setWidth(168);
		tblclmnProveedor.setText("Proveedor");
		
		Button btnActualizar = new Button(this, SWT.NONE);
		btnActualizar.setBounds(522, 385, 105, 35);
		btnActualizar.setText("Actualizar");
		
		Button btnNuevo = new Button(this, SWT.NONE);
		btnNuevo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNuevo.setBounds(668, 385, 105, 35);
		btnNuevo.setText("Nuevo");
		
		Button btnAgregar = new Button(this, SWT.NONE);
		btnAgregar.setBounds(800, 385, 105, 35);
		btnAgregar.setText("Agregar");
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
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Compra.class, new String[]{"codigoCompra", "fechaCompra", "cantidadCompra", "factura_Compra.totalCompra", "proveedorAsociado.nombreProveedor"});
		tableViewer_2.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer_2.setContentProvider(listContentProvider);
		//
		IObservableList listaComprasFerreteriaObserveList = PojoProperties.list("listaCompras").observe(ferreteria);
		tableViewer_2.setInput(listaComprasFerreteriaObserveList);
		//
		return bindingContext;
	}
}
