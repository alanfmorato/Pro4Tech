package ClassesConexao;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

import javax.swing.border.BevelBorder;

import javax.swing.border.BevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaVisualizacao extends JFrame {

	private JPanel contentPane;
	private JTable tbDescVaga;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaVisualizacao frame = new TelaVisualizacao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaVisualizacao() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		setExtendedState(MAXIMIZED_BOTH);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Ariane Sousa\\Desktop\\PROJETOS\\Pro4Tech\\icons\\iconPro4Tech.jpg"));
		lblNewLabel.setBounds(0, 0, 553, 90);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("VOCÊ ESTÁ NA PÁGINA DE VISUALIZAÇÃO DE VAGAS!");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel_1.setBounds(144, 138, 634, 33);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("APERTE NO BOTÃO DE BUSCAR PARA VIZUALIZAR AS VAGAS ABERTAS!");
		
		
		JLabel lblNewLabel_1_11 = new JLabel("DIGITE O NOME DA VAGA DESEJADA E CLIQUE EM BUSCAR VAGAS.");
		lblNewLabel_1_11.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel_1_11.setBounds(144, 166, 840, 33);
		contentPane.add(lblNewLabel_1_11);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("SELECIONE A VAGA PARA VISUALIZAR DETALHES E SE CANDIDATAR.");
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel_1_1_1.setBounds(144, 243, 910, 33);
		contentPane.add(lblNewLabel_1_1_1);
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
				try {
					
					Connection con = Conexao.faz_conexao();
					
					String sql = "select nome_vaga, carga_horaria, requisitos, escolaridade from Vagas";
					PreparedStatement stmt = con.prepareStatement(sql);
					ResultSet rs = stmt.executeQuery();
					
					
					DefaultTableModel modelo = (DefaultTableModel) tbDescVaga.getModel();
					modelo.setNumRows(0);
					
					while (rs.next()) {
						modelo.addRow(new Object[] {rs.getString("nome_vaga"), rs.getString("carga_horaria"), rs.getString("requisitos"), rs.getString("escolaridade") });
					}
					rs.close();
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		scrollPane_1.setFont(new Font("Arial", Font.PLAIN, 17));
		scrollPane_1.setBounds(321, 318, 878, 385);
		contentPane.add(scrollPane_1);
		
		tbDescVaga = new JTable();
		tbDescVaga.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Singleton.getInstance().nomeVaga = tbDescVaga.getValueAt(tbDescVaga.getSelectedRow(), 0).toString();
			}
		});
		tbDescVaga.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
					
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		tbDescVaga.setBackground(Color.WHITE);
		tbDescVaga.setFont(new Font("Arial", Font.PLAIN, 17));
		tbDescVaga.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"NOME VAGA", "CARGA HOR\u00C1RIA", "REQUISITOS", "ESCOLARIDADE"
			}
		));
		scrollPane_1.setViewportView(tbDescVaga);

		JButton btnVoltar = new JButton("VOLTAR");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaOpcoes exibir = new TelaOpcoes();
				exibir.setVisible(true);
				setVisible(false);
		
			}
		});
		btnVoltar.setForeground(Color.BLACK);
		btnVoltar.setFont(new Font("Arial", Font.BOLD, 18));
		btnVoltar.setBorderPainted(false);
		btnVoltar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnVoltar.setBackground(new Color(255, 140, 0));
		btnVoltar.setBounds(10, 768, 156, 52);
		contentPane.add(btnVoltar);
		
		JButton btnNewButton = new JButton("CANDIDATAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = Conexao.faz_conexao();
					String sql = "insert into candidato_vaga(cpf, nome_vaga, status) values (?, ?, ?)";
					PreparedStatement stmt = con.prepareStatement(sql);
					
					stmt.setString(2, Singleton.getInstance().nomeVaga);
					stmt.setString(1, Singleton.getInstance().cpfUsuario);
					stmt.setString(3, "ANDAMENTO");
					stmt.execute();
					stmt.close();
					con.close();

					JOptionPane.showMessageDialog(null, "CANDIDATURA REALIZADA!");
				} catch (Exception e2) {
					
				}
				
				
			}
		});
		btnNewButton.setBackground(new Color(255, 140, 0));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 18));
		btnNewButton.setBounds(645, 746, 247, 60);
		contentPane.add(btnNewButton);
		
	}
}
