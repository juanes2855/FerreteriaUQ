package Ferreteria.app.view;

import javax.swing.JOptionPane;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;

import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.controller.CrudProductoViewController;
import Ferreteria.app.controller.ModelFactoryController;
import Ferreteria.app.model.Ferreteria;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import Ferreteria.app.model.Producto;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;

public class ProductoView extends Composite {

	CrudProductoViewController crudProductoViewController = new CrudProductoViewController();
	Ferreteria ferreteria = crudProductoViewController.getFerreteria();
	ModelFactoryController model = new ModelFactoryController();

	private DataBindingContext dataBindingContext = null;
	private Table table;
	private Text textNombreProd;
	private Text textCodigoProd;
	private Text textPrecioProd;
	private Text textCategoriaProd;
	private Text textMarcaProd;
	private TableViewer tableProductos;

	Producto productoSeleccionado;

	public ProductoView(Composite parent, int style) {
		super(parent, style);

		Group grpAcciones = new Group(this, SWT.NONE);
		grpAcciones.setText("Acciones");
		grpAcciones.setBounds(10, 10, 918, 92);

		Button btnNuevoProducto = new Button(grpAcciones, SWT.NONE);
		btnNuevoProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				limpiarCampoTexto();
			}
		});
		btnNuevoProducto.setBounds(523, 32, 167, 35);
		btnNuevoProducto.setText("Nuevo Producto");

		Button btnEliminarProducto = new Button(grpAcciones, SWT.NONE);
		btnEliminarProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Boolean flag = false;
				if(productoSeleccionado != null){
					flag= crudProductoViewController.eliminarProducto(productoSeleccionado.getCodigoProducto());
					limpiarCampoTexto();
					initDataBindings();
					
					if(flag == true){
						JOptionPane.showMessageDialog(null, "El producto se elimino con exito");
					}else{
						JOptionPane.showMessageDialog(null, "No se pudo eliminar");
					}
				}
			}
		});
		btnEliminarProducto.setBounds(717, 32, 167, 35);
		btnEliminarProducto.setText("Eliminar Producto");

		Group grpListaProductos = new Group(this, SWT.NONE);
		grpListaProductos.setText("Lista Productos");
		grpListaProductos.setBounds(10, 108, 918, 274);

		tableProductos = new TableViewer(grpListaProductos, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableProductos.getTable();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				 
				productoSeleccionado= (Producto) table.getItem(table.getSelectionIndex()).getData();
				
				textCategoriaProd.setText(productoSeleccionado.getCategoria());
				textCodigoProd.setText(Integer.toString(productoSeleccionado.getCodigoProducto()));
				textMarcaProd.setText(productoSeleccionado.getMarca());
				textNombreProd.setText(productoSeleccionado.getNombreProducto());
				textPrecioProd.setText(Double.toString(productoSeleccionado.getPrecio()));
				
			}
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setBounds(10, 28, 898, 236);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(232);
		tblclmnNombre.setText("Nombre ");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnCdigo = tableViewerColumn_1.getColumn();
		tblclmnCdigo.setWidth(128);
		tblclmnCdigo.setText("C\u00F3digo");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnPrecio = tableViewerColumn_2.getColumn();
		tblclmnPrecio.setWidth(163);
		tblclmnPrecio.setText("Precio");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnCategoria = tableViewerColumn_3.getColumn();
		tblclmnCategoria.setWidth(185);
		tblclmnCategoria.setText("Categoria");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnMarca = tableViewerColumn_4.getColumn();
		tblclmnMarca.setWidth(186);
		tblclmnMarca.setText("Marca");

		Group grpDetalles = new Group(this, SWT.NONE);
		grpDetalles.setText("Detalles");
		grpDetalles.setBounds(10, 388, 918, 189);

		Label lblNombre = new Label(grpDetalles, SWT.NONE);
		lblNombre.setBounds(10, 35, 81, 25);
		lblNombre.setText("Nombre");

		Label lblCdigo = new Label(grpDetalles, SWT.NONE);
		lblCdigo.setBounds(10, 83, 81, 25);
		lblCdigo.setText("C\u00F3digo");

		Label lblPrecio = new Label(grpDetalles, SWT.NONE);
		lblPrecio.setBounds(10, 133, 81, 25);
		lblPrecio.setText("Precio");

		textNombreProd = new Text(grpDetalles, SWT.BORDER);
		textNombreProd.setBounds(91, 35, 248, 31);

		textCodigoProd = new Text(grpDetalles, SWT.BORDER);
		textCodigoProd.setBounds(91, 83, 248, 31);

		textPrecioProd = new Text(grpDetalles, SWT.BORDER);
		textPrecioProd.setBounds(91, 133, 248, 31);

		Label lblCategoria = new Label(grpDetalles, SWT.NONE);
		lblCategoria.setBounds(482, 35, 81, 25);
		lblCategoria.setText("Categoria");

		Label lblMarca = new Label(grpDetalles, SWT.NONE);
		lblMarca.setBounds(482, 83, 81, 25);
		lblMarca.setText("Marca");

		textCategoriaProd = new Text(grpDetalles, SWT.BORDER);
		textCategoriaProd.setBounds(582, 35, 248, 31);

		textMarcaProd = new Text(grpDetalles, SWT.BORDER);
		textMarcaProd.setBounds(582, 83, 248, 31);

		Button btnAgregarProducto = new Button(grpDetalles, SWT.NONE);
		btnAgregarProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (verificarCamposVacios() == true) {

					Producto productoNuevo = new Producto();

					try {

						productoNuevo.setCategoria(textCategoriaProd.getText());
						productoNuevo.setCodigoProducto(Integer.parseInt(textCodigoProd.getText()));
						productoNuevo.setMarca(textMarcaProd.getText());
						productoNuevo.setNombreProducto(textNombreProd.getText());
						productoNuevo.setPrecio(Double.valueOf(textPrecioProd.getText()));

						crudProductoViewController.crearProducto(productoNuevo);
						initDataBindings();
						limpiarCampoTexto();

					} catch (yaExiste e1) {
						JOptionPane.showMessageDialog(null, "El empleado ya existe", null, JOptionPane.WARNING_MESSAGE,
								null);
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "No se aceptan letras en código ó precio", null,
								JOptionPane.WARNING_MESSAGE, null);
					}
				}
			}
		});
		btnAgregarProducto.setBounds(444, 144, 166, 35);
		btnAgregarProducto.setText("Agregar Producto");

		Button btnActualizarProducto = new Button(grpDetalles, SWT.NONE);
		btnActualizarProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (productoSeleccionado != null) {
					crudProductoViewController.actualizarProducto(textNombreProd.getText(),
							Integer.valueOf(textCodigoProd.getText()), Double.valueOf(textPrecioProd.getText()),
							textCategoriaProd.getText(), textMarcaProd.getText());
					initDataBindings();
				}
			}
		});
		btnActualizarProducto.setBounds(629, 144, 166, 35);
		btnActualizarProducto.setText("Actualizar Producto");
		dataBindingContext = initDataBindings();

	}

	public void limpiarCampoTexto() {
		textCategoriaProd.setText("");
		textCodigoProd.setText("");
		textMarcaProd.setText("");
		textNombreProd.setText("");
		textPrecioProd.setText("");
	}

	public Boolean verificarCamposVacios() {

		if (textCategoriaProd.getText().equalsIgnoreCase("") || textCodigoProd.getText().equalsIgnoreCase("")
				|| textMarcaProd.getText().equalsIgnoreCase("") || textNombreProd.getText().equalsIgnoreCase("")
				|| textPrecioProd.getText().equalsIgnoreCase("")) {

			return false;
		} else {

			return true;
		}
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
				Producto.class, new String[] { "nombreProducto", "codigoProducto", "precio", "categoria", "marca" });
		tableProductos.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableProductos.setContentProvider(listContentProvider);
		//
		IObservableList listaProductosFerreteriaObserveList = PojoProperties.list("listaProductos").observe(ferreteria);
		tableProductos.setInput(listaProductosFerreteriaObserveList);
		//
		return bindingContext;
	}
}
