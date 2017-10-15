import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class encoder {
	
	public static HashMap<String,Integer> buildFrequencyTable(File file) throws IOException
	{
		HashMap<String,Integer> fmap=new HashMap<String,Integer>();
		FileInputStream fis=new FileInputStream(file);
		BufferedReader br= new BufferedReader(new InputStreamReader(fis));
		String line="";
		while((line=br.readLine())!=null && !(line.trim().equals("")))
		{
			
			if(fmap.containsKey(line))
			{
				fmap.put(line, fmap.get(line)+1);
			}
			else
				fmap.put(line, 1);
				
			
		}
		br.close();
		return fmap;
		
	}
	public static BinaryHeap buildTreeFromBinaryHeap(HashMap<String,Integer> frequency_table)
	{
		//long starttime = System.currentTimeMillis();
		BinaryHeap binaryHeap=new BinaryHeap(1000000);
	
		
		Iterator it=frequency_table.entrySet().iterator();
		while(it.hasNext())
		{
			
			Map.Entry<String,Integer> set= (Map.Entry<String,Integer>)it.next();
			binaryHeap.insertBinaryHeap(new HuffmanTree(set.getKey(),set.getValue()));
		}
		
		while(binaryHeap.BinaryHeapSize()>1)
		{
		HuffmanTree tempNode1=binaryHeap.deleteMinElement();
		HuffmanTree tempNode2=binaryHeap.deleteMinElement();
		HuffmanTree newNode= new HuffmanTree(null,tempNode1.frequency+tempNode2.frequency);
		newNode.left=tempNode1;
		newNode.right=tempNode2;
		binaryHeap.insertBinaryHeap(newNode);
		
		}
	
		
		//System.out.println("Binary tree time: "+(System.currentTimeMillis()- starttime)/1000);
		return binaryHeap;
	}
	
	public static FourWayHeap buildTreeFromFourWayHeap(HashMap<String,Integer> frequency_table)
	{
		long starttime = System.currentTimeMillis();
		FourWayHeap fourWayHeap=new FourWayHeap(1000000);
		for(int i=0;i<10;i++)
		{
		Iterator it=frequency_table.entrySet().iterator();
		while(it.hasNext())
		{
			
			Map.Entry<String,Integer> set= (Map.Entry<String,Integer>)it.next();
			fourWayHeap.insertToFourWayHeap(new HuffmanTree(set.getKey(),set.getValue()));
		}
		
		
		while(!(fourWayHeap.isEmpty()))
		{
		HuffmanTree tempNode1=fourWayHeap.poll();
		
		if(!fourWayHeap.isEmpty())
		{
			HuffmanTree tempNode2=fourWayHeap.poll();
		int combinedFrequency=tempNode1.frequency+tempNode2.frequency;
		HuffmanTree newNode= new HuffmanTree(null,combinedFrequency);
		newNode.left=tempNode1;
		newNode.right=tempNode2;
		fourWayHeap.insertToFourWayHeap(newNode);
		
		}
		else
		{
			fourWayHeap.insertToFourWayHeap(tempNode1);
			break;
		}
		}
		}
		System.out.println("4 way Heap tree :"+(System.currentTimeMillis()- starttime)/1000);
		return fourWayHeap;
	}
	
	
	public static PairingHeap buildTreeFromPairingHeap(HashMap<String,Integer> frequency_table)
	{
		//long starttime = System.currentTimeMillis();
		PairingHeap pairingHeap=new PairingHeap();
		
			
		Iterator it=frequency_table.entrySet().iterator();
		while(it.hasNext())
		{
			
			Map.Entry<String,Integer> set= (Map.Entry<String,Integer>)it.next();
			pairingHeap.insertPariningHeap(new HuffmanTree(set.getKey(),set.getValue()));
		}
		
		
		while(!(pairingHeap.size()>1))
		{
		HuffmanTree tempNode1=pairingHeap.deleteMin();
		
		
		HuffmanTree tempNode2=pairingHeap.deleteMin();;
		int combinedFrequency=tempNode1.frequency+tempNode2.frequency;
		HuffmanTree newNode= new HuffmanTree(null,combinedFrequency);
		newNode.left=tempNode1;
		newNode.right=tempNode2;
		pairingHeap.insertPariningHeap(newNode);
		
		}
		
		
		//System.out.println("Pairing Heap tree "+(System.currentTimeMillis()- starttime)/1000);
		return pairingHeap;
		
	}
	
	
	public static HashMap<String,String> buildHuffmanTreeAndCodeTable(HashMap<String,Integer> frequency_table)
	{
		HashMap<String,String> code_table=new HashMap<String,String>();
		
		
		BinaryHeap binaryHeap=buildTreeFromBinaryHeap(frequency_table);
		generateCodeTable(binaryHeap.deleteMinElement(),code_table,"");
		
		
		//PairingHeap pairingHeap=buildTreeFromPairingHeap(frequency_table);
		//generateCodeTable(pairingHeap.deleteMin(),code_table,"");
		
		//FourWayHeap fourWayHeap=buildTreeFromFourWayHeap(frequency_table);
		//generateCodeTable(fourWayHeap.poll(),code_table,"");
		
		
	
	
	    return code_table;
		
		
		
	}

	public static void generateCodeTable(HuffmanTree root,HashMap<String,String> code_table,String path)
	{ 	
		if(root!=null)
		{	
			generateCodeTable(root.left,code_table,path+"0");
			if(root.left==null && root.right==null)
			{
			code_table.put(root.data, path);
			}
			
			generateCodeTable(root.right,code_table,path+"1");
		
		}		
	}
	
	public static void encodeData(File inputFile,HashMap<String,String> code_table)
	{
		
		final String encodedFileName="encoded.bin";
		final String code_tableFileName="code_table.txt";
		FileOutputStream fileOutputStream=null;
		File encodeFile= new File(encodedFileName);
		StringBuilder encodeString=new StringBuilder("");
		
		try
		{
			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
			String line=null;
			while((line=br.readLine())!=null)
			{
				encodeString.append(code_table.get(line.trim()));
				
				
			}
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("File Read Exception in input file" + e);
		}
		try
		{
			fileOutputStream= new FileOutputStream(encodeFile);
			if(!encodeFile.exists())
			{
				encodeFile.createNewFile();
			}
			
			int index=0;
			byte[] byteArray=new byte[encodeString.length()/8];
			int count=0;
			while(index<encodeString.length()-7)
			{
				byte nextByteChunk=0x00;
				
				for(int i=0;i<8;i++)
				{
					nextByteChunk=(byte)(nextByteChunk<<1);
					nextByteChunk+=encodeString.charAt(index+i)=='0'?0x0:0x1;
				}
				
				byteArray[count]=nextByteChunk;
				count++;
				index=index+8;
				
			}
			fileOutputStream.write(byteArray);		
			
		}
		catch(IOException e)
		{
			System.out.println("File Write Exception in encoded.bin" + e);
		}
		
		try
		{
			BufferedWriter bw1 =new BufferedWriter(new FileWriter(code_tableFileName));
			Iterator it = code_table.entrySet().iterator();
			while(it.hasNext())
			{
				
				Map.Entry<String,String> sets= (Map.Entry<String,String>)it.next();
				bw1.write(sets.getKey()+" "+sets.getValue());
				bw1.newLine();
				
			}
			
			bw1.close();
		}
		catch(IOException e)
		{
			System.out.println("File Write Exception in code_table.txt" + e);
		}
	}



public static void main(String[] args) {
	// TODO Auto-generated method stub
	long starttime = System.currentTimeMillis();
	File input_file= new File(args[0]);
	HashMap<String,Integer> freq_table= new HashMap<String,Integer>(); 
	HashMap<String,String> code_table= new HashMap<String,String>(); 
	
	
	
	try{
		freq_table=buildFrequencyTable(input_file);
	}
	catch(Exception ex)
	{
		System.out.println("Input Output Excep"+ ex);
	}
	
	
	
	code_table=buildHuffmanTreeAndCodeTable(freq_table);
	
	encodeData(input_file,code_table);
	System.out.println("encode done "+(System.currentTimeMillis()- starttime)/1000);

}

}

