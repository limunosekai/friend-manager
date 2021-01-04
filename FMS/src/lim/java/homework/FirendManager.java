package lim.java.homework;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class FirendManager extends Frame implements ActionListener, KeyListener, ItemListener {
	private Dialog d = new Dialog(this, "Friend Manager Version", false);
	private File myFile = null;
	private FileDialog fileDialog;
	private MenuBar mb = new MenuBar();
	private Menu menu_file = new Menu("File");
	private Menu menu_help = new Menu("Help");
	private MenuItem file_new = new MenuItem("New");
	private MenuItem file_save = new MenuItem("Save");
	private MenuItem file_load = new MenuItem("Load");
	private MenuItem file_saveAs = new MenuItem("Save as New name");
	private MenuItem file_exit = new MenuItem("Exit");
	private MenuItem help_ver = new MenuItem("Version");
	private Label empty1 = new Label("");
	private Label empty2 = new Label("");
	private Label empty3 = new Label("");
	private Label empty4 = new Label("");
	private Label title1 = new Label("전체 목록", Label.CENTER);
	private Label title2 = new Label("개 인 정 보 입 력", Label.CENTER);
	private Label title3 = new Label("개 인 정 보 분 석", Label.CENTER);
	private Label lb_name = new Label("이    름 : ", Label.RIGHT);
	private Label lb_id = new Label("주    민 : ", Label.RIGHT);
	private Label lb_tel = new Label("전    화 : ", Label.RIGHT);
	private Label lb_sex = new Label("성    별 : ", Label.RIGHT);
	private Label lb_hobby = new Label("취    미 : ", Label.RIGHT);
	private Label id_sep = new Label("-", Label.CENTER);
	private Label tel_sep1 = new Label("-", Label.CENTER);
	private Label tel_sep2 = new Label("-", Label.CENTER);
	private Label dial_text = new Label("Version 1.0", Label.CENTER);
	private TextField tf_name = new TextField(20);
	private TextField tf_frontId = new TextField(6);
	private TextField tf_backId = new TextField(7);
	private TextField tf_frontTel = new TextField(4);
	private TextField tf_backTel = new TextField(4);
	private List list = new List(45);
	private TextArea ta_analysis = new TextArea(30, 40);
	private CheckboxGroup cb_group = new CheckboxGroup();
	private Checkbox cb_male = new Checkbox("남성", cb_group, true);
	private Checkbox cb_female = new Checkbox("여성", cb_group, false);
	private Checkbox[] hobby_arr = new Checkbox[5];
	private String[] hobby_str = { "독서", "영화", "음악", "게임", "쇼핑" };
	private Choice choice_tel = new Choice();
	private Button[] btn_arr = new Button[5];
	private Button dial_ok = new Button("확인");
	private String[] btn_str = { "등록", "분석", "수정", "삭제", "지우기" };
	private boolean mod = false;
	private int btn_selected = 0;
	private ArrayList<Friends> friend = new ArrayList<>();
	private String name;
	private String age;
	private String sex;
	private String address;
	private String birth;
	private String id;
	private String tel;
	private Boolean[] hobby = new Boolean[5];
	public int year = 1900;

	public FirendManager() {// 생성자
		super("Friend Manager");
		setForm();
		setEvent();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(500, 600);
		setResizable(false);
		setLocation(500, 180);
		setVisible(true);
	}

	public void setForm() {// 레이아웃
		// 레이아웃
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout());
		add("West", empty1);
		add("East", empty2);
		add("South", empty3);
		add("North", empty4);
		Panel wrapper_upDown = new Panel(new GridLayout(2, 1));
		add("Center", wrapper_upDown);
		Panel wrapper_leftRight = new Panel(new BorderLayout());
		Panel content_analysis = new Panel(new BorderLayout());
		wrapper_upDown.add(wrapper_leftRight);
		wrapper_upDown.add(content_analysis);
		Panel content_input = new Panel(new BorderLayout(5, 10));
		Panel content_list = new Panel(new BorderLayout());
		wrapper_leftRight.add("Center", content_input);
		wrapper_leftRight.add("East", content_list);

		// 메뉴바
		setMenuBar(mb);
		mb.add(menu_file);
		mb.add(menu_help);
		menu_file.add(file_new);
		menu_file.addSeparator();
		menu_file.add(file_save);
		menu_file.add(file_load);
		menu_file.add(file_saveAs);
		menu_file.addSeparator();
		menu_file.add(file_exit);
		menu_help.add(help_ver);

		// 개인정보입력
		content_input.add("North", title2);

		// 개인정보 항목
		Panel ci_items = new Panel(new GridLayout(5, 1));
		ci_items.add(lb_name);
		ci_items.add(lb_id);
		ci_items.add(lb_tel);
		ci_items.add(lb_sex);
		ci_items.add(lb_hobby);
		content_input.add("West", ci_items);

		// 개인정보 이름 입력
		Panel ci_inputs = new Panel(new GridLayout(5, 1));
		Panel ci_input_name = new Panel(new FlowLayout(FlowLayout.LEFT));
		ci_input_name.add(tf_name);
		ci_inputs.add(ci_input_name);

		// 개인정보 주민 입력
		Panel ci_input_id = new Panel(new FlowLayout(FlowLayout.LEFT));
		ci_input_id.add(tf_frontId);
		ci_input_id.add(id_sep);
		ci_input_id.add(tf_backId);
		ci_inputs.add(ci_input_id);

		// 개인정보 전번 입력
		Panel ci_input_tel = new Panel(new FlowLayout(FlowLayout.LEFT));
		String[] tel = { "010", "011", "016", "017", "019" };
		for (int i = 0; i < tel.length; i++) {
			choice_tel.add(tel[i]);
		}
		ci_input_tel.add(choice_tel);
		ci_input_tel.add(tel_sep1);
		ci_input_tel.add(tf_frontTel);
		ci_input_tel.add(tel_sep2);
		ci_input_tel.add(tf_backTel);
		ci_inputs.add(ci_input_tel);

		// 개인정보 성별 입력
		Panel ci_input_sex = new Panel(new FlowLayout(FlowLayout.LEFT));
		ci_input_sex.add(cb_male);
		ci_input_sex.add(cb_female);
		ci_inputs.add(ci_input_sex);

		// 개인정보 취미 입력
		Panel ci_input_hobby = new Panel(new FlowLayout(FlowLayout.LEFT));
		for (int i = 0; i < hobby_arr.length; i++) {
			hobby_arr[i] = new Checkbox(hobby_str[i]);
			ci_input_hobby.add(hobby_arr[i]);
		}
		ci_inputs.add(ci_input_hobby);

		// 버튼
		Panel ci_input_btn = new Panel(new FlowLayout(FlowLayout.CENTER));
		for (int i = 0; i < btn_arr.length; i++) {
			btn_arr[i] = new Button(btn_str[i]);
			ci_input_btn.add(btn_arr[i]);
		}
		content_input.add("South", ci_input_btn);
		content_input.add("Center", ci_inputs);

		// 전체목록
		content_list.add("North", title1);
		content_list.add("Center", list);

		// 개인정보 분석
		content_analysis.add("North", title3);
		content_analysis.add("Center", ta_analysis);

		// 초기 버튼 비활성화
		setMod();
	}

	public void setDialog() {// 다이얼로그 레이아웃
		d.setLayout(new BorderLayout());
		d.setBackground(Color.LIGHT_GRAY);
		d.add("Center", dial_text);
		d.add("South", dial_ok);
		d.setSize(150, 150);
		d.setVisible(true);
		dial_ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d.dispose();
			}
		});
	}

	public void setEvent() {// 이벤트 걸기
		tf_frontId.addKeyListener(this);
		tf_backId.addKeyListener(this);
		choice_tel.addItemListener(this);
		list.addItemListener(this);
		for (int i = 0; i < btn_arr.length; i++) {
			btn_arr[i].addActionListener(this);
		}
		file_exit.addActionListener(this);
		help_ver.addActionListener(this);
		file_new.addActionListener(this);
		file_save.addActionListener(this);
		file_load.addActionListener(this);
		file_saveAs.addActionListener(this);
	}

	public void setMod() {// 버튼 활성/비활성
		for (int j = 0; j < btn_arr.length; j++) {
			if (j == 0) {
				btn_arr[j].setEnabled(!mod);
			} else {
				btn_arr[j].setEnabled(mod);
			}
		}
	}

	public void initForm() {// 화면 초기화
		tf_name.setText("");
		tf_frontId.setText("");
		tf_backId.setText("");
		tf_frontTel.setText("");
		tf_backTel.setText("");
		cb_group.setSelectedCheckbox(cb_male);
		for (int i = 0; i < hobby_arr.length; i++) {
			hobby_arr[i].setState(false);
		}
		choice_tel.select(0);
		choice_tel.select(0);
		tf_name.setEditable(true);
		tf_frontId.setEditable(true);
		tf_backId.setEditable(true);
		cb_male.setEnabled(true);
		cb_female.setEnabled(true);
		mod = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < btn_arr.length; i++) {
			if (e.getSource() == btn_arr[i]) {
				btn_selected = i;
				button_event();
			}
		}
		setMod();
		// 나가기
		if (e.getSource() == file_exit) {
			System.exit(0);
		}
		// 버전 정보
		if (e.getSource() == help_ver) {
			Point pt = getLocation();
			Dimension main_size = getSize();
			Dimension dial_size = d.getSize();
			d.setLocation((int) pt.getX() + main_size.width / 2 - dial_size.width / 2,
					(int) pt.getY() + main_size.height / 2 - dial_size.height / 2);
			setDialog();
		}
		// 새 파일
		if (e.getSource() == file_new) {
			friend.clear();
			list.removeAll();
			myFile = null;
			mod = false;
			
		}
		// 저장하기
		if (e.getSource() == file_save) {
			if(myFile == null) {
				fileDialog = new FileDialog(this, "저장하기", FileDialog.SAVE);
				fileDialog.setVisible(true);
				
				String fileName = fileDialog.getFile();
				String directory = fileDialog.getDirectory();
				
				if(fileName == null || directory == null) {
					return;
				}
				
				myFile = new File(directory, fileName);	
			}
			saveObj();
		}
		// 불러오기
		if (e.getSource() == file_load) {
			fileDialog = new FileDialog(this, "불러오기", FileDialog.LOAD);
			fileDialog.setVisible(true);
			
			String fileName = fileDialog.getFile();
			String directory = fileDialog.getDirectory();
			
			if(fileName == null || directory == null) {
				return;
			}
			
			myFile = new File(directory, fileName);
			loadObj();
		}
		// 새 이름으로 저장하기
		if (e.getSource() == file_saveAs) {
			fileDialog = new FileDialog(this, "새 이름으로 저장하기", FileDialog.SAVE);
			fileDialog.setVisible(true);
			
			String fileName = fileDialog.getFile();
			String directory = fileDialog.getDirectory();
			
			if(fileName == null || directory == null) {
				return;
			}
			
			myFile = new File(directory, fileName);
			saveObj();
		}
	}
		
	public void saveObj() {//데이터 저장
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(myFile);
			oos = new ObjectOutputStream(fos);
			
			for(int i=0; i<friend.size(); i++) {
				Friends myFriend = friend.get(i);
				oos.writeObject(myFriend);
			}
			ta_analysis.setText("\n\t성공적으로 저장되었습니다.");
		} catch(FileNotFoundException fnfe) {
			System.err.println(myFile.getName()+"파일이 존재하지 않습니다.");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try { if(oos != null) oos.close(); } catch(IOException ioe) {}
			try { if(fos != null) fos.close(); } catch(IOException ioe) {}
		}
	}
	
	public void loadObj() {//데이터 불러오기
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		list.removeAll();
		friend.clear();

		try {
			fis = new FileInputStream(myFile);
			ois = new ObjectInputStream(fis);
			Friends myFriend = null;
			
			while((myFriend = (Friends)ois.readObject()) != null) {
				list.add(myFriend.getName());
				friend.add(myFriend);
			}
			ta_analysis.setText("\n\t성공적으로 불러왔습니다.");
		} catch(EOFException eofe) {
			ta_analysis.setText("데이터 로딩이 완료되었습니다.");
		} catch(FileNotFoundException e) {
			System.err.println(myFile.getName()+"파일이 존재하지 않습니다.");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			System.err.println("관리자에게 문의하세요.");
		}finally {
			try { if(ois != null) ois.close(); } catch(IOException ioe) {}
			try { if(fis != null) fis.close(); } catch(IOException ioe) {}
		}
	}
	
	public void make_info() {// 신상정보 구하기
		// 이름 구하기
		name = tf_name.getText();
		// 생년월일 구하기
		year = 1900;
		switch ((int) (id.charAt(6) - 48)) {
		case '3':
		case '4':
			year = 2000;
			break;
		case '9':
		case '0':
			year = 1800;
			break;
		default:
			year = 1900;
			break;
		}
		year += (int) (id.charAt(0) - 48) * 10 + (int) (id.charAt(1) - 48);
		int month = (int) (id.charAt(2) - 48) * 10 + (int) (id.charAt(3) - 48);
		int day = (int) (id.charAt(4) - 48) * 10 + (int) (id.charAt(5) - 48);
		birth = String.valueOf(year) + "년 " + String.valueOf(month) + "월 " + String.valueOf(day) + "일";
		// 성별 구하기
		sex = (int) (id.charAt(6) - 48) % 2 == 0 ? "여성" : "남성";
		// 나이 구하기
		age = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - year) + "세";
		// 출생 구하기
		String[] where = { "서울", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주" };
		address = where[(int) (id.charAt(7) - 48)] + "지역";
	}

	public void button_event() {// 버튼 이벤트
		int index = list.getSelectedIndex();
		switch (btn_selected) {
		case 0:
			// 데이터 입력 안받고 등록 버튼 눌렀을때
			if (tf_name.getText().isEmpty() || tf_frontId.getText().isEmpty() || tf_backId.getText().isEmpty()
					|| tf_frontTel.getText().isEmpty() || tf_backTel.getText().isEmpty()) {
				ta_analysis.setText("입력을 해주세요");
				return;
			} else {
				id = tf_frontId.getText() + tf_backId.getText();
				tel = choice_tel.getSelectedItem()+tf_frontTel.getText() + tf_backTel.getText();
				for (int i = 0; i < hobby_arr.length; i++) {
					hobby[i] = hobby_arr[i].getState();
				}
				// 주민 유효성 검사
				int id_total = 0;
				int id_multi = 2;
				float temp = 0;
				double hap = 0;
				for (int i = 0; i < id.length() - 1; i++) {
					id_total += ((int) (id.charAt(i) - 48) * id_multi);
					id_multi++;
					if (id_multi == 10)
						id_multi = 2;
				}
				temp = 11.f * (int) (id_total / 11.f) + 11.f - id_total;
				hap = temp - 10.f * (int) (temp / 10.f);
				if (hap != (int) (id.charAt(id.length() - 1) - 48)) {
					ta_analysis.setText("경고 : 올바르지 않은 주민등록번호입니다.");
					return;
				}
				make_info();

				// 리스트에 추가
				list.add(name);
				ta_analysis.setText(name + "님이 성공적으로 등록되었습니다.");
				friend.add(new Friends(id, name, birth, age, sex, address, tel, hobby));

				initForm();
			}
			break;
		case 1:
			ta_analysis.setText("\n\t이름 : " + friend.get(index).getName() + "\n\t생년 : " + friend.get(index).getBirth()
					+ "\n\t나이 : " + friend.get(index).getAge() + "\n\t성별 : " + friend.get(index).getSex() + "\n\t출생 : "
					+ friend.get(index).getAddress());
			break;
		case 2:
			String newTel = choice_tel.getSelectedItem()+tf_frontTel.getText() + tf_backTel.getText();
			friend.get(index).setTel(newTel);
			Boolean[] newHobby = new Boolean[5];
			for (int i = 0; i < hobby_arr.length; i++) {
				newHobby[i] = hobby_arr[i].getState();
			}
			friend.get(index).setHobby(newHobby);
			ta_analysis.setText(friend.get(index).getName() + "님의 정보가 성공적으로 수정되었습니다.");
			break;
		case 3:
			ta_analysis.setText(friend.get(index).getName() + "님의 정보가 삭제되었습니다.");
			friend.remove(index);
			list.remove(index);

			initForm();
			break;
		case 4:
			initForm();
			ta_analysis.setText("");
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == tf_frontId) {
			if (tf_frontId.getText().trim().length() == 6) {
				tf_backId.requestFocus();
				return;
			}
		} else if (e.getSource() == tf_backId) {
			if (tf_backId.getText().trim().length() == 7) {
				choice_tel.requestFocus();
				return;
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == choice_tel) {
			tf_frontTel.requestFocus();
			return;
		}
		if (e.getSource() == list) {
			// 화면 표시
			tf_name.setEditable(false);
			tf_frontId.setEditable(false);
			tf_backId.setEditable(false);
			cb_male.setEnabled(false);
			cb_female.setEnabled(false);
			int index = list.getSelectedIndex();
			tf_name.setText(friend.get(index).getName());
			tf_frontId.setText(friend.get(index).getId().substring(0,6));
			tf_backId.setText(friend.get(index).getId().substring(6));
			choice_tel.select(friend.get(index).getTel().substring(0,3));
			tf_frontTel.setText(friend.get(index).getTel().substring(3,7));
			tf_backTel.setText(friend.get(index).getTel().substring(7));
			if (friend.get(index).getSex().equals("남성")) {
				cb_group.setSelectedCheckbox(cb_male);
			} else if (friend.get(index).getSex().equals("여성")) {
				cb_group.setSelectedCheckbox(cb_female);
			}
			if (e.getStateChange() == 1) {
				for (int i = 0; i < hobby_arr.length; i++) {
					hobby_arr[i].setState(friend.get(index).getHobby()[i]);
				}
			}
			// 버튼 활성화
			mod = true;
			setMod();
		}
	}

	public static void main(String[] args) {
		new FirendManager();
	}
}
