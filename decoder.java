import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class decoder {
	
	public static HuffmanDecoderTree generateDecoderTree(File code_table_file)
	{
		HuffmanDecoderTree root=new HuffmanDecoderTree();
		try{
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(code_table_file)));
			String line=null;
			while((line=br.readLine())!=null)
			{
			String[] codes=line.split("\\s+");	
			
			char[] codePath=codes[1].toCharArray(); 
			HuffmanDecoderTree newroot=root;
			for(int i=0;i<codePath.length;i++)
			{
				
				if(codePath[i]=='0')
				{
					if(newroot.left==null)
					{
					newroot.left=new HuffmanDecoderTree();
					}
					newroot=newroot.left;
				}
				if(codePath[i]=='1')
				{
					if(newroot.right==null)
					{
					newroot.right=new HuffmanDecoderTree();
					}
					newroot=newroot.right;
				}
			}
			newroot.data(codes[0].toString());
				
			}
			br.close();
			}
			catch(IOException e)
			{
				System.out.println("File Write Exception in code_table_file.txt" + e);
			}
		return root;
	}
	public static void decodeData(File code_table_file,File encoded_file)
	{
		HuffmanDecoderTree root=generateDecoderTree(code_table_file);
		
		FileOutputStream outputStream= null;
  		File decodedFile;
  		
		try
		{
			decodedFile=new File("decoder.txt");
			outputStream = new FileOutputStream(decodedFile);
			
			if(!decodedFile.exists()){
				decodedFile.createNewFile();
			}
			Path path = Paths.get(encoded_file.getAbsolutePath());
			byte[] binaryFile = Files.readAllBytes(path);
  			String bitStr="";
  			HuffmanDecoderTree node;
  			StringBuilder stringBuilder = new StringBuilder("");
  			for(byte binary: binaryFile){
  				node=root;
  				bitStr += String.format("%8s", Integer.toBinaryString(binary & 0xFF)).replace(' ', '0');
  				for(int i=0; i<bitStr.length(); i++){
  					if(bitStr.charAt(i) == '0'){
  						node=node.left;
  					}else if(bitStr.charAt(i) == '1'){
  						node=node.right;
  					}
  					if(node.left==null && node.right==null){
  						stringBuilder.append(node.data+"\n");
  						node = root;
  						if(i+1 < bitStr.length()){
  							bitStr = bitStr.substring(i+1);
  						}else{
  							bitStr="";
  						}
  						i=-1;
  					}
  				}
  			}
  			outputStream.write(stringBuilder.toString().getBytes());
	
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Error in reading the encoding_file.txt"+ e);
			
		}
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File code_table_file= new File(args[0]);
		File encoded_file = new File(args[1]);
		
		long starttime = System.currentTimeMillis();
		
		
		decodeData(code_table_file,encoded_file);
		
		System.out.println("Decoding done "+(System.currentTimeMillis()- starttime)/1000);
	}

}



