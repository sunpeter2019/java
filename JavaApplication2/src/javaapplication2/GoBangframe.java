
package javaapplication2;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GoBangframe extends JPanel implements GoBangconfig{
    public Graphics g; //画笔
    public int[][] isAvail= new int[15][15]; //棋盘的落子情况
    public ArrayList<ChessPosition>ChessPositionList=new ArrayList<ChessPosition>();
    public int turn =0;//控制游戏进行，控制白棋还是黑棋
    public int ChooseType=0;//0表示人人对战，1表示人机对战，默认人人对战
    
    //设置不同情况的权值，大小代表这个点的选择优先程度//设置不同落子情况和相应权值的数组
    public static HashMap<String,Integer> map = new HashMap<String,Integer>();
	static {
		
		//被堵住
		map.put("01", 17);//眠1连
		map.put("02", 12);//眠1连
		map.put("001", 17);//眠1连
		map.put("002", 12);//眠1连
		map.put("0001", 17);//眠1连
		map.put("0002", 12);//眠1连
		
		map.put("0102",17);//眠1连，15
		map.put("0201",12);//眠1连，10
		map.put("0012",15);//眠1连，15
		map.put("0021",10);//眠1连，10
		map.put("01002",19);//眠1连，15
		map.put("02001",14);//眠1连，10
		map.put("00102",17);//眠1连，15
		map.put("00201",12);//眠1连，10
		map.put("00012",15);//眠1连，15
		map.put("00021",10);//眠1连，10

		map.put("01000",21);//活1连，15
		map.put("02000",16);//活1连，10
		map.put("00100",19);//活1连，15
		map.put("00200",14);//活1连，10
		map.put("00010",17);//活1连，15
		map.put("00020",12);//活1连，10
		map.put("00001",15);//活1连，15
		map.put("00002",10);//活1连，10

		//被堵住
		map.put("0101",65);//眠2连，40
		map.put("0202",60);//眠2连，30
		map.put("0110",65);//眠2连，40
		map.put("0220",60);//眠2连，30
		map.put("011",65);//眠2连，40
		map.put("022",60);//眠2连，30
		map.put("0011",65);//眠2连，40
		map.put("0022",60);//眠2连，30
		
		map.put("01012",65);//眠2连，40
		map.put("02021",60);//眠2连，30
		map.put("01102",65);//眠2连，40
		map.put("02201",60);//眠2连，30
		map.put("00112",65);//眠2连，40
		map.put("00221",60);//眠2连，30

		map.put("01010",75);//活2连，40
		map.put("02020",70);//活2连，30
		map.put("01100",75);//活2连，40
		map.put("02200",70);//活2连，30
		map.put("00110",75);//活2连，40
		map.put("00220",70);//活2连，30
		map.put("00011",75);//活2连，40
		map.put("00022",70);//活2连，30
		
		//被堵住
		map.put("0111",150);//眠3连，100
		map.put("0222",140);//眠3连，80
		
		map.put("01112",150);//眠3连，100
		map.put("02221",140);//眠3连，80
		
		map.put("01101",1000);//活3连，130
		map.put("02202",800);//活3连，110
		map.put("01011",1000);//活3连，130
		map.put("02022",800);//活3连，110
		map.put("01110", 1000);//活3连
		map.put("02220", 800);//活3连
		
		map.put("01111",3000);//4连，300
		map.put("02222",3500);//4连，280
	}
    //定义一个二维数组，保存各个点的权值
    public int[][] weightArray=new int[column][row];
    
    public static void main(String[] args) {
            GoBangframe gf=new GoBangframe();//初始化一个五子棋界面的对象
            gf.initUI();//调用方法进行界面的初始化
    }
    
    public void initUI(){
            //初始化一个界面,并设置标题大小等属性
            JFrame jf=new JFrame();
            jf.setTitle("五子棋");
            jf.setSize(800, 650);
            jf.setLocationRelativeTo(null);//设置窗口相对于指定组件的位置。null，则此窗口将置于屏幕的中央
            jf.setDefaultCloseOperation(3);////EXIT_ON_CLOSE,直接关闭应用程序，System.exit(0)。一个main函数对应一整个程序。 
            
            jf.setLayout(new BorderLayout() );//设置顶级容器JFrame为框架布局
            
            Dimension dim1=new Dimension(150,0);//设置右半部分的大小
            Dimension dim3=new Dimension(550,0);//设置左半部分的大小
            Dimension dim2=new Dimension(140,40);//设置右边按钮组件的大小
            
            //实现左边的界面，把GoBangframe的对象添加到框架布局的中间部分
            this.setPreferredSize(dim3);//设置下棋界面的大小
            this.setBackground(Color.LIGHT_GRAY);//设置下棋界面的颜色       
            jf.add(this,BorderLayout.CENTER);//添加到框架布局的中间部分
            
            //实现右边的JPanel容器界面
            JPanel jp=new JPanel();
            jp.setPreferredSize(dim1);
            jp.setBackground(Color.white);
            jf.add(jp,BorderLayout.EAST);
            jp.setLayout(new FlowLayout());//设置JPanel为流式布局
            
            //接下来我们需要把按钮等组件依次加到那个JPanel上面
            //设置按钮数组
            String[] butname= {"开始新游戏","悔棋","认输"};
            JButton[] button=new JButton[3];
            
            //依次把三个按钮组件加上去
            for(int i=0;i<butname.length;i++){
                    button[i]=new JButton(butname[i]);
                    button[i].setPreferredSize(dim2);
                    jp.add(button[i]);
            }
            //设置选项按钮
            String[] boxname= {"人人对战","人机对战"};
            JComboBox box=new JComboBox(boxname);
            jp.add(box);
            
            //按钮监控类
            ButtonListener butListen=new ButtonListener(this,box);
            
            //对每一个按钮都添加状态事件的监听处理机制
            for(int i=0;i<butname.length;i++){ 
                button[i].addActionListener(butListen);
            }
             
            //对可选框添加事件监听机制
	    box.addActionListener(butListen);
            
            frameListener fl=new frameListener();
            fl.setGraphics(this);//获取画笔对象
            this.addMouseListener(fl);
                
            jf.setVisible(true);
    }
    
    public void PopUp(String result) {
		JOptionPane jo=new JOptionPane();
		jo.showMessageDialog(null, result, "游戏结果", JOptionPane.PLAIN_MESSAGE);
	}
    
    //重写重绘方法,这里重写的是第一个大的JPanel的方法
    @Override
    public void paint(Graphics g){   // x,y,row,column?
            super.paint(g);
            
            Image icon= new ImageIcon("chessboard.jpg").getImage();	//这里不能用ImageIcon  
            g.drawImage(icon, 0, 0, row*size, column*size, null);

            /*//重绘出棋盘
            g.setColor(Color.BLACK);
            for(int i=0;i<row;i++){
                    g.drawLine(x, y+size*i, x+size*(column-1), y+size*i);
            }
            for(int j=0;j<column;j++){
                    g.drawLine(x+size*j, y, x+size*j, y+size*(row-1)); 
            }*/
            
            //重绘出棋子
            for(int i=0;i<row;i++){
                for(int j=0;j<column;j++){
                    if(isAvail[i][j]==1){
                        int countx=size*j+20;//棋盘坐标到位置的转化
                        int county=size*i+20;
                        //g.setColor(Color.black);
                        //public abstract void fillOval(int x,int y,int width,int height)使用当前颜色填充外接指定矩形框的椭圆。
                        //g.fillOval(countx-size/2, county-size/2, size, size);
                        g.drawImage(blackchess,countx-size/2, county-size/2, size, size,null);
                        //public abstract boolean drawImage(Image img,int x,int y,int width,int height,ImageObserver observer)
                        //x - x 坐标。y - y 坐标。width - 矩形的宽度。height - 矩形的高度。observer - 当转换了更多图像时要通知的对象。
                    }
                    else if(isAvail[i][j]==2){
                        int countx=size*j+20;
                        int county=size*i+20;
                        //g.setColor(Color.white);
                        //g.fillOval(countx-size/2, county-size/2, size, size);
                        g.drawImage(whitechess,countx-size/2, county-size/2, size, size,null);
                    }
                }
            }
    }
}
            