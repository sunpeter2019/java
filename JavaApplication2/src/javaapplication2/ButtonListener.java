
package javaapplication2;

/**
 *
 * @author Fool。
 */
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

//实现对JPanel的监听接口处理
public class ButtonListener implements GoBangconfig,ActionListener{
    public GoBangframe gf;
    public JComboBox box;
    
    public ButtonListener(GoBangframe gf,JComboBox box){//添加box作为参数，传入combobox，并对其监听
        this.gf=gf; //获取左半部分的画板
        this.box=box;//获取可选框对象
    }
    @Override
    //当界面发生操作时进行处理
    public void actionPerformed(ActionEvent ae) {
        //获取当前被点击按钮的内容，判断是不是"开始新游戏"这个按钮
        if(ae.getActionCommand().equals("开始新游戏")){
            //重绘棋盘
		    for(int i=0;i<gf.isAvail.length;i++)
			   for(int j=0;j<gf.isAvail[i].length;j++)
			    	 gf.isAvail[i][j]=0;
		    gf.repaint();
                    //如果是开始新游戏的按钮，再为左半部分设置监听方法
                    gf.turn=1;
        }
        else if(ae.getActionCommand().equals("悔棋")){
                if(gf.ChessPositionList.size()>1){
                        ChessPosition l=new ChessPosition();
                        l=gf.ChessPositionList.remove(gf.ChessPositionList.size()-1);
                        gf.isAvail[l.Listi][l.Listj]=0;
                        if(gf.turn==1)gf.turn++;
                        else gf.turn--;
                        //直接调用gf的重绘方法，重绘方法的画笔应该是在棋盘页面还没生成的时候就要获取
			//调用repaint会自动调用paint方法，而且不用给参数
			gf.repaint();
			//gf.paint(gf.getGraphics());
                }
                else {
				System.out.println("不能悔棋!");
			}
        }
        else if(ae.getActionCommand().equals("认输")){
                if(gf.turn==1)System.out.println("白方赢");
                else System.out.println("黑方赢");
                //重新把棋盘设置为不可操作
		    gf.turn=0;
        }
        else if(box.getSelectedItem().equals("人机对战")) {
			 gf.ChooseType=1;
			 gf.turn=0;
                         System.out.println("人机");
		}
	else if(box.getSelectedItem().equals("人人对战")){ //? 监听不到box的信号
			 gf.ChooseType=0;
			 gf.turn=0;
                         System.out.println("人人");
		}
        
    }
    
    
}
