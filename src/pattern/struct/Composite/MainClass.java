package pattern.struct.Composite;
import java.util.List;

/*
 * tree
 */

public class MainClass {
	public static void main(String[] args) {
		//C��
		Folder rootFolder = new Folder("C:");
		//beifengĿ¼
		Folder beifengFolder = new Folder("beifeng");
		//beifeng.txt�ļ�
		File beifengFile = new File("beifeng.txt");
		
		rootFolder.add(beifengFolder);
		rootFolder.add(beifengFile);
		
		//ibeifengĿ¼
		Folder ibeifengFolder = new Folder("ibeifeng");
		File ibeifengFile = new File("ibeifeng.txt");
		beifengFolder.add(ibeifengFolder);
		beifengFolder.add(ibeifengFile);
		
		Folder iibeifengFolder = new Folder("iibeifeng");
		File iibeifengFile = new File("iibeifeng.txt");
		ibeifengFolder.add(iibeifengFolder);
		ibeifengFolder.add(iibeifengFile);
		
		displayTree(rootFolder,0);
		
	}
	
	public static void displayTree(IFile rootFolder, int deep) {
		for(int i = 0; i < deep; i++) {
			System.out.print("--");
		}
		//��ʾ���������
		rootFolder.display();
		//�������
		List<IFile> children = rootFolder.getChild();
		//��������
		for(IFile file : children) {
			if(file instanceof File) {
				for(int i = 0; i <= deep; i++) {
					System.out.print("--");
				}
				file.display();
			} else {
				displayTree(file,deep + 1);
			}
		}
	}
}
