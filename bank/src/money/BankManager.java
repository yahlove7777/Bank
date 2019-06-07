package money;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BankManager extends JFrame implements ActionListener {

	JButton btinsert, btupdate, btdelete, btselect, btselectall, btninsert, btncancel;
	JTextField tfId, tfname, tfage, tftel;
	JTextField tfTel1, tfTel2, tfTel3;
	JPanel pbtn;
	GridBagLayout gb;
	GridBagConstraints gbc;

	public static void main(String[] args) {
		new BankManager();
	}

	public BankManager() {

		this.setTitle("고객 관리");

		pbtn = new JPanel();
		pbtn.setLayout(null);
		btinsert = new JButton("신규고객 삽입");
		btinsert.setBounds(50, 50, 150, 50);
		pbtn.add(btinsert);
		getContentPane().add(pbtn);

		btupdate = new JButton("고객정보 수정");
		btupdate.setBounds(200, 50, 150, 50);
		pbtn.add(btupdate);

		btdelete = new JButton("고객정보 삭제");
		btdelete.setBounds(350, 50, 150, 50);
		pbtn.add(btdelete);

		btselect = new JButton("고객정보 검색");
		btselect.setBounds(500, 50, 150, 50);
		pbtn.add(btselect);

		btselectall = new JButton("고객정보 전체검색");
		btselectall.setBounds(650, 50, 150, 50);
		pbtn.add(btselectall);

		btinsert.addActionListener(this);
		btupdate.addActionListener(this);
		btdelete.addActionListener(this);
		btselect.addActionListener(this);
		btselectall.addActionListener(this);

		setSize(850, 200);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void createUI() {

		this.setTitle("고객삽입");
		gb = new GridBagLayout();
		setLayout(gb);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		// 아이디
		JLabel bId = new JLabel("아이디: ");
		tfId = new JTextField(20);
		// 그리드백에 붙이기
		gbAdd(bId, 0, 0, 1, 1);
		gbAdd(tfId, 1, 0, 3, 1);

		// 이름
		JLabel bname = new JLabel("이름: ");
		tfname = new JTextField(20);
		gbAdd(bname, 0, 1, 1, 1);
		gbAdd(tfname, 1, 1, 3, 1);

		// 나이
		JLabel bage = new JLabel("나이 :");
		tfage = new JTextField(20);
		gbAdd(bage, 0, 2, 1, 1);
		gbAdd(tfage, 1, 2, 3, 1);

		// 전화번호
		JLabel bTel = new JLabel("전화번호 :");
		JPanel pTel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tfTel1 = new JTextField(6);
		tfTel2 = new JTextField(6);
		tfTel3 = new JTextField(6);
		pTel.add(tfTel1);
		pTel.add(new JLabel(" - "));
		pTel.add(tfTel2);
		pTel.add(new JLabel(" - "));
		pTel.add(tfTel3);
		gbAdd(bTel, 0, 3, 1, 1);
		gbAdd(pTel, 1, 3, 3, 1);

		JPanel pButton = new JPanel();
		btninsert = new JButton("가입");
		pButton.add(btninsert);
		btncancel = new JButton("취소");
		pButton.add(btncancel);
		gbAdd(pButton, 0, 10, 4, 1);

		// 버튼에 감지기를 붙이자
		btninsert.addActionListener(this);
		btncancel.addActionListener(this);

		setSize(350, 200);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //System.exit(0) //프로그램종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // dispose(); //현재창만 닫는다.
	}

	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2, 2, 2, 2);
		add(c, gbc);
	}// gbAdd
	

	private void insertMember() {

		
		
		// 화면에서 사용자가 입력한 내용을 얻는다.
		BankDTO dto = getViewData();
		BankDAO dao = new BankDAO();
		boolean ok = dao.insertMember(dto);

		if (ok) {

			JOptionPane.showMessageDialog(this, "등록이 완료되었습니다.");
			dispose();

		} else {

			JOptionPane.showMessageDialog(this, "등록이 정상적으로 처리되지 않았습니다.");
		}

	}// insertMember
	
	private void UpdateMember() {
		String id = JOptionPane.showInputDialog("ID 입력");
		String tel = JOptionPane.showInputDialog("변경할 TEL 입력");
		
		//정보로 DB를 수정
		BankDAO dao = new BankDAO();
		boolean ok = dao.updateMember(id, tel);

		if (ok) {
			JOptionPane.showMessageDialog(this, "수정되었습니다.");
		} else {
			JOptionPane.showMessageDialog(this, "수정실패: 값을 확인하세요");
		}
	}
	
	private void deleteMember() {
		String id = JOptionPane.showInputDialog("삭제할 ID 입력");

		// System.out.println(mList);
		BankDAO dao = new BankDAO();
		boolean ok = dao.deleteMember(id);

		if (ok) {
			JOptionPane.showMessageDialog(this, "삭제완료");

		} else {
			JOptionPane.showMessageDialog(this, "삭제실패");

		}

	}// deleteMember
	
	private void SelectallMember() {
		
		BankDAO dao = new BankDAO();
		ArrayList list = dao.selectall();

		
		for (int i = 0; i < list.size(); i++) {
			BankDTO dto = (BankDTO)list.get(i);
			System.out.println("검색된 ID : " + dto.getId());
			System.out.println("검색된 NAME : " + dto.getName());
			System.out.println("검색된 AGE : " + dto.getAge());
			System.out.println("검색된 TEL : " + dto.getTel());
			System.out.println();
		}

	}
	
	private void SelectMember() {
		String id = JOptionPane.showInputDialog("검색할 ID 입력");
		createUI();
		btninsert.setEnabled(false);
		btninsert.setVisible(false);
		BankDAO dao = new BankDAO();
		BankDTO dto = dao.getMemberDTO(id);
		viewData(dto);
		
	}
	
	
	public BankDTO getViewData() {

		BankDTO dto = new BankDTO();
		String id = tfId.getText();
		String name = tfname.getText();
		String age = tfage.getText();
		String tel1 = tfTel1.getText();
		String tel2 = tfTel2.getText();
		String tel3 = tfTel3.getText();
		String tel = tel1 + "-" + tel2 + "-" + tel3;

		dto.setId(id);
		dto.setName(name);
		dto.setAge(age);
		dto.setTel(tel);

		return dto;
	}
	
	private void viewData(BankDTO vMem) {

		String id = vMem.getId();
		String name = vMem.getName();
		String age = vMem.getAge();
		String tel = vMem.getTel();

		// 화면에 세팅
		tfId.setText(id);
		tfId.setEditable(false); // 편집 안되게
		tfname.setText(name);
		tfage.setText(age);
		String[] tel2 = tel.split("-");
		tfTel1.setText(tel2[0]);
		tfTel2.setText(tel2[1]);
		tfTel3.setText(tel2[2]);

	}// viewData

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btinsert) {
			createUI();
		} else if (e.getSource() == btncancel) {
			this.dispose();
			new BankManager();
		} else if (e.getSource() == btninsert) {
			insertMember();
			new BankManager();
		} else if (e.getSource() == btupdate) {
			UpdateMember();
		}else if (e.getSource() == btdelete) {
			deleteMember();
		}else if (e.getSource() == btselectall) {
			SelectallMember();
		}else if (e.getSource() == btselect) {
			SelectMember();
		}
	}
}
