package ec.sife.views.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class PrincipalFrm extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JDesktopPane dpContenedor;
    TipoPrecioFrm tipoPrecioFrm;
    CategoriaProductoFrm  categoriaProductoFrm;
    TipoClienteFrm tipoPersonaFrm;
    TipoIdentificacionPersonaFrm tipoIdentificacionPersonaFrm;
    CrearPersonaFrm crearPersonaFrm;
    TarifaIvaFrm tarifaIfaFrm;
    TarifaIceFrm tarifaIceFrm;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PrincipalFrm frame = new PrincipalFrm();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PrincipalFrm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnMantenimiento = new JMenu("Mantenimiento");
        menuBar.add(mnMantenimiento);
        
        JMenu mnProductos = new JMenu("Productos");
        mnMantenimiento.add(mnProductos);
        
        JMenuItem mntmProductos = new JMenuItem("Productos");
        mnProductos.add(mntmProductos);
        
        JMenuItem mntmTCategorias = new JMenuItem("Categorias");
        mntmTCategorias.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(categoriaProductoFrm==null || categoriaProductoFrm.isClosed()){
        			categoriaProductoFrm=new CategoriaProductoFrm();
                    dpContenedor.add(categoriaProductoFrm);
                    categoriaProductoFrm.show();
                }
        	}
        });
        mnProductos.add(mntmTCategorias);
        
        JMenuItem mntmTipoDePrecios = new JMenuItem("Tipo de Precios");
        mntmTipoDePrecios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(tipoPrecioFrm==null || tipoPrecioFrm.isClosed()){
                    tipoPrecioFrm=new TipoPrecioFrm();
                    dpContenedor.add(tipoPrecioFrm);
                    tipoPrecioFrm.show();
                }
                
            }
        });
        mnMantenimiento.add(mntmTipoDePrecios);
        
        JMenuItem mntmPersona = new JMenuItem("Persona");
        mntmPersona.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		
        		if(tipoPersonaFrm==null || tipoPersonaFrm.isClosed()){
        			tipoPersonaFrm=new TipoClienteFrm();
                    dpContenedor.add(tipoPersonaFrm);
                    tipoPersonaFrm.show();
                }
        	}
        });
        mnMantenimiento.add(mntmPersona);
        
        JMenuItem mntmIdentificacionPersona = new JMenuItem("Identificacion Persona");
        mntmIdentificacionPersona.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(tipoIdentificacionPersonaFrm==null || tipoIdentificacionPersonaFrm.isClosed()){
        			tipoIdentificacionPersonaFrm=new TipoIdentificacionPersonaFrm();
                    dpContenedor.add(tipoIdentificacionPersonaFrm);
                    tipoIdentificacionPersonaFrm.show();
                }	
        		
        	}
        });
        
        JMenu mnNewMenu = new JMenu("Persona");
        mnMantenimiento.add(mnNewMenu);
        
        JMenuItem mntmCliente = new JMenuItem("Cliente");
        mntmCliente.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		if(crearPersonaFrm==null || crearPersonaFrm.isClosed()){
        			crearPersonaFrm=new CrearPersonaFrm();
                    dpContenedor.add(crearPersonaFrm);
                    crearPersonaFrm.show();
                }	
        		
        		
        		
        	}
        });
        mnNewMenu.add(mntmCliente);
        mnMantenimiento.add(mntmIdentificacionPersona);
        
        JMenu mnSri = new JMenu("SRI");
        menuBar.add(mnSri);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Tarifa de Iva");
        mntmNewMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(tarifaIfaFrm==null || tarifaIfaFrm.isClosed()){
        			tarifaIfaFrm=new TarifaIvaFrm();
                    dpContenedor.add(tarifaIfaFrm);
                    tarifaIfaFrm.show();
                }	
        	}
        });
        mnSri.add(mntmNewMenuItem);
        
        JMenuItem mntmTarifaDeIce = new JMenuItem("Tarifa de Ice");
        mntmTarifaDeIce.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		if(tarifaIceFrm==null || tarifaIceFrm.isClosed()){
        			tarifaIceFrm=new TarifaIceFrm();
                    dpContenedor.add(tarifaIceFrm);
                    tarifaIceFrm.show();
                }	
        	}
        });
        mnSri.add(mntmTarifaDeIce);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        dpContenedor = new JDesktopPane();
        dpContenedor.setBackground(Color.LIGHT_GRAY);
        contentPane.add(dpContenedor, BorderLayout.CENTER);
    }
}
