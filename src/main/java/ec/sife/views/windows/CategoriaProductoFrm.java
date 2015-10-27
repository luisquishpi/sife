package ec.sife.views.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;

public class CategoriaProductoFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea txtNombre;
	private JTextArea txtDependencia;
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

	public CategoriaProductoFrm() {
		setIconifiable(true);
		setClosable(true);
		crearControles();
		crearEventos();
		cargarArbolCategoriaProductos();
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

	public void cargarArbolCategoriaProductos() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Categorias                          ");
		modelo = new DefaultTreeModel(raiz);
		getHojas(raiz, "0");
		tree.setModel(modelo);

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
			txtDependencia.setText("Categorias                          ");
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
					CategoriaProducto catProducto = (CategoriaProducto) nodo.getUserObject();
					lblId.setText(catProducto.getId().toString());
					txtNombre.setText(catProducto.getNombre());
					txtDependencia.setText(nodo.getParent().toString());
					lblDepen.setText(catProducto.getDependencia().toString());
					chbxContieneProductos.setSelected(catProducto.getContieneProductos());
					System.out.println("raiz " + nodo.getParent());

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
				
				;
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
					// System.out.println("Valor" + lblDepen.getText());

				} else {
					

					CategoriaProducto categoriaProducto = new CategoriaProducto(Integer.parseInt(lblId.getText()),
							txtNombre.getText(), Integer.parseInt(lblDepen.getText()),
							chbxContieneProductos.isSelected());
					CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
					if( categoriaProductoController.updateCategoriaProducto(categoriaProducto))
					{								
						actualizarNodoArbol(categoriaProducto);
					}
					else		
					{
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		tree = new JTree();
		contentPane.add(tree, BorderLayout.WEST);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		txtNombre = new JTextArea();

		lblNombre = new JLabel("Nombre:");

		lblDependencia = new JLabel("Dependencia:");

		txtDependencia = new JTextArea();

		chbxContieneProductos = new JCheckBox("Contiene productos");
		txtDependencia.setEditable(false);
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(CategoriaProductoFrm.class.getResource("/ec/sife/images/cancel.png")));

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(CategoriaProductoFrm.class.getResource("/ec/sife/images/save.png")));
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(CategoriaProductoFrm.class.getResource("/ec/sife/images/new.png")));
		lblDepen = new JLabel("0");

		lblId = new JLabel("0");
		lblId.setForeground(Color.DARK_GRAY);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(34)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDependencia, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(36)
									.addComponent(lblDepen)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(chbxContieneProductos)
								.addComponent(txtDependencia, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
								.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblId)))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNuevo, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnGuardar, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(83)
					.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombre)
						.addComponent(txtNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDependencia)
						.addComponent(txtDependencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(chbxContieneProductos)
						.addComponent(lblDepen))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuevo, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnGuardar, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
					.addGap(87))
		);
		panel.setLayout(gl_panel);
		lblId.setVisible(false);
		lblDepen.setVisible(false);
	}
}
