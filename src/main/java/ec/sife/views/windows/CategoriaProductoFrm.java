package ec.sife.views.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class CategoriaProductoFrm extends JFrame {

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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoriaProductoFrm frame = new CategoriaProductoFrm();
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void getHojas(DefaultMutableTreeNode raiz, String id) {

		CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
		List<CategoriaProducto> lista;
		lista = categoriaProductoController.CategoriaProductoList(Integer.parseInt(id));

		for (int i = 0; i < lista.size(); i++) {
			// DefaultMutableTreeNode hoja = new DefaultMutableTreeNode(
			// lista.get(i).getId()+"."+lista.get(i).getNombre() );
			// getHojas( hoja ,lista.get(i).getId().toString());
			// raiz.add( hoja );
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
	public CategoriaProductoFrm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {

				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				if(nodo.getLevel()!=0)
				{							
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
		contentPane.add(tree, BorderLayout.WEST);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		txtNombre = new JTextArea();

		JLabel lblNombre = new JLabel("Nombre:");

		JLabel lblDependencia = new JLabel("Dependencia:");

		txtDependencia = new JTextArea();

		chbxContieneProductos = new JCheckBox("Contiene productos");
		chbxContieneProductos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				
				if(chbxContieneProductos.isSelected()==true)
				{
				  btnNuevo.setEnabled(false);
				}
				else
				{
					btnNuevo.setEnabled(true);
				}
			}
		});

		//// Cargar dastos en el arbol
		cargarArbolCategoriaProductos();
		txtDependencia.setEditable(false);
		//lblDepen.setVisible(false);
	

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(nodo.getLevel()==0)
				{
					lblDepen.setText("0");
					txtDependencia.setText("Categorias                          ");	
				}
				else
				{
					lblDepen.setText(lblId.getText());
					txtDependencia.setText(txtNombre.getText());
					
				}
									
				lblId.setText("0");				
				txtNombre.setText("");
				chbxContieneProductos.setSelected(false);			
				txtNombre.requestFocus();
;
			}
		});

		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
						
						
				if(lblId.getText().toString()=="0")
				{
					System.out.println("Valor"+lblDepen.getText());
					
					
					CategoriaProducto categoriaProducto = new CategoriaProducto(txtNombre.getText(), Integer.parseInt(lblDepen.getText()),chbxContieneProductos.isSelected());
					CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
					categoriaProductoController.saveCategoriaProducto(categoriaProducto);
					System.out.println("Valor"+lblDepen.getText());
					
				}
				else		{
					
					CategoriaProducto categoriaProducto = new CategoriaProducto(Integer.parseInt(lblId.getText()), txtNombre.getText(), Integer.parseInt(lblDepen.getText()),chbxContieneProductos.isSelected());
					CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
					categoriaProductoController.updateCategoriaProducto(categoriaProducto);
					System.out.println(categoriaProducto);
					
				}
				tree=new JTree();
				cargarArbolCategoriaProductos();
				
				((DefaultTreeModel)tree.getModel()).reload();
				//System.out.println(categoriaProducto);
			}

		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
				List<CategoriaProducto> lista;
				lista = categoriaProductoController.CategoriaProductoList();

				for (CategoriaProducto categoriaProducto : lista) {

					System.out.println("Me gusta " + categoriaProducto);
				}

				System.out.println("cero> " + lista.get(0).getNombre());
				System.out.println("Lista" + lista);
				System.out.println("Initial size of al: " + lista.size());

			}
		});
		
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
							.addComponent(btnNuevo, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnGuardar)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnCancelar)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(83)
					.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
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
						.addComponent(btnNuevo)
						.addComponent(btnGuardar)
						.addComponent(btnCancelar))
					.addGap(87))
		);
		panel.setLayout(gl_panel);
		lblId.setVisible(false);
		lblDepen.setVisible(false);
	}
}
