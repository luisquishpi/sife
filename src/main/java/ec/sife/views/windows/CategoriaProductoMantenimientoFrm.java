package ec.sife.views.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import ec.sife.controllers.CategoriaProductoController;
import ec.sife.models.entities.CategoriaProducto;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import javax.swing.JTextField;

public class CategoriaProductoMantenimientoFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTree tree;
	private DefaultTreeModel modelo;
	private JLabel lblId;
	private JLabel lblDepen;
	private JCheckBox chbxContieneProductos;
	private JButton btnNuevo;
	private JLabel lblNombre;
	private JLabel lblDependencia;
	private JButton btnGuardar;
	private JPanel panel;
	private JButton btnCancelar;
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField txtNombre;
	private JTextField txtDependencia;
	private CategoriaProducto categoriaProducto;

	public CategoriaProductoMantenimientoFrm() {
		setIconifiable(true);
		setClosable(true);
		setTitle("Categorías de Productos");
		crearControles();
		crearEventos();
		cargarArbolCategoriaProductos();

	}

	public void cargarArbolCategoriaProductos() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Categorías de Productos");
		modelo = new DefaultTreeModel(raiz);
		getHojas(raiz, "0");
		tree.setModel(modelo);
	}

	public void getHojas(DefaultMutableTreeNode raiz, String id) {
		CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
		List<CategoriaProducto> lista;
		lista = categoriaProductoController.CategoriaProductoList(Integer.parseInt(id));
		for (int i = 0; i < lista.size(); i++) {
			DefaultMutableTreeNode hoja = new DefaultMutableTreeNode();
			hoja.setUserObject(lista.get(i));
			getHojas(hoja, lista.get(i).getId().toString());
			raiz.add(hoja);
		}
	}

	private void limpiarcampos() {
		btnGuardar.setText("Guardar");
		btnGuardar.setIcon(new ImageIcon(TipoPrecioFrm.class.getResource("/ec/sife/images/save.png")));

		lblId.setText("0");
		txtNombre.setText("");
		chbxContieneProductos.setSelected(false);
		txtNombre.requestFocus();

	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || lblDependencia.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void verificarUbicacionNodoNuevo() {
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (nodo.getLevel() == 0) {
			lblDepen.setText("0");
			txtDependencia.setText("Categorias");
		} else {
			lblDepen.setText(lblId.getText());
			txtDependencia.setText(txtNombre.getText());
		}
	}

	private void agregarNodoArbol(CategoriaProducto categoriaProducto) {
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		model.insertNodeInto(new DefaultMutableTreeNode(categoriaProducto), nodo, model.getChildCount(nodo));

	}

	private void actualizarNodoArbol(CategoriaProducto categoriaProducto) {
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		nodo.setUserObject(categoriaProducto);
		((DefaultTreeModel) tree.getModel()).nodeChanged(nodo);

	}

	private void crearEventos() {
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				btnGuardar.setText("Actualizar");
				btnGuardar.setIcon(new ImageIcon(TipoPrecioFrm.class.getResource("/ec/sife/images/update.png")));
				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (nodo.getLevel() != 0) {
					categoriaProducto = (CategoriaProducto) nodo.getUserObject();
					lblId.setText(categoriaProducto.getId().toString());
					txtNombre.setText(categoriaProducto.getNombre());
					txtDependencia.setText(nodo.getParent().toString());
					lblDepen.setText(categoriaProducto.getDependencia().toString());
					chbxContieneProductos.setSelected(categoriaProducto.getContieneProductos());
					System.out.println("raiz " + nodo.getParent());
					System.out.println("Cate: " + categoriaProducto);
				}
			}
		});

		chbxContieneProductos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (chbxContieneProductos.isSelected() == true) {
					btnNuevo.setEnabled(false);
				} else {
					btnNuevo.setEnabled(true);
				}
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verificarUbicacionNodoNuevo();
				limpiarcampos();
			}

		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "No deje campos vacíos");
					return;
				}
				if (lblId.getText().toString() == "0") {
					CategoriaProducto categoriaProducto = new CategoriaProducto(txtNombre.getText(),
							Integer.parseInt(lblDepen.getText()), chbxContieneProductos.isSelected());
					CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
					categoriaProductoController.saveCategoriaProducto(categoriaProducto);
					if (categoriaProducto.getId() != 0) {
						agregarNodoArbol(categoriaProducto);
						limpiarcampos();
					}
				} else {
					CategoriaProducto categoriaProducto = new CategoriaProducto(Integer.parseInt(lblId.getText()),
							txtNombre.getText(), Integer.parseInt(lblDepen.getText()),
							chbxContieneProductos.isSelected());
					CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
					if (categoriaProductoController.updateCategoriaProducto(categoriaProducto)) {
						actualizarNodoArbol(categoriaProducto);
					} else {
						JOptionPane.showMessageDialog(null, "Error, no es posible actualizar");
						return;
					}
					System.out.println(categoriaProducto);
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}

	private void crearControles() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 601, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnNuevo = new JButton("Nuevo");
		panel_1.add(btnNuevo);
		btnNuevo.setIcon(new ImageIcon(CategoriaProductoMantenimientoFrm.class.getResource("/ec/sife/images/new.png")));

		btnGuardar = new JButton("Guardar");
		panel_1.add(btnGuardar);
		btnGuardar.setIcon(
				new ImageIcon(CategoriaProductoMantenimientoFrm.class.getResource("/ec/sife/images/save.png")));
		btnCancelar = new JButton("Cancelar");
		panel_1.add(btnCancelar);
		btnCancelar.setIcon(
				new ImageIcon(CategoriaProductoMantenimientoFrm.class.getResource("/ec/sife/images/cancel.png")));

		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);

		lblId = new JLabel("0");
		lblId.setForeground(Color.DARK_GRAY);
		lblId.setVisible(false);

		chbxContieneProductos = new JCheckBox("Contiene productos");
		lblDepen = new JLabel("0");
		lblDepen.setVisible(false);

		lblNombre = new JLabel("Nombre:");

		lblDependencia = new JLabel("Dependencia:");

		txtNombre = new JTextField();
		txtNombre.setColumns(10);

		txtDependencia = new JTextField();
		txtDependencia.setEditable(false);
		txtDependencia.setColumns(10);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
						.addComponent(lblDependencia, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGap(385))
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(chbxContieneProductos, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblNombre, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
				.addGroup(gl_panel_2.createSequentialGroup().addGap(62)
						.addComponent(lblDepen, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addGap(56)
						.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(278, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(txtNombre, Alignment.LEADING).addComponent(txtDependencia,
										Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
						.addContainerGap(147, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap().addComponent(lblNombre).addGap(3)
						.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblDependencia)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtDependencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(9).addComponent(chbxContieneProductos)
						.addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel_2
								.createParallelGroup(Alignment.BASELINE).addComponent(lblDepen).addComponent(lblId))
				.addContainerGap(85, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.WEST);
		scrollPane.setPreferredSize(new Dimension(200, 200));

		tree = new JTree();
		scrollPane.setViewportView(tree);
	}

}
