
class BinaryHeap
{
	private int binaryHeapSize;
	HuffmanTree[] binaryHeap;
	
	public BinaryHeap(int capacitySize)
	{
		binaryHeapSize=0;
		binaryHeap=new HuffmanTree[capacitySize+1];
		
	}
	
	public boolean isBinaryHeapEmpty()
	{
		return binaryHeapSize==0; 
	}
	
	public boolean isBinaryHeapFull()
	{
		return binaryHeapSize==binaryHeap.length;
	}
	
	private int getParent(int index)
	{
		return (index-1)/2;
	}
	public int BinaryHeapSize()
	{
		return binaryHeapSize;
	}
	
/*	private int getLeftChild(int index)
	{
		return (2*index)+1;
	}
	
	private int getRightChild(int index)
	{
		return (2*index)+2;
	}*/
	
	public void insertBinaryHeap(HuffmanTree node)
	{
		binaryHeap[binaryHeapSize++]=node;
		heapifyUp(binaryHeapSize-1);
		//System.out.println("after "+ node.frequency);
		//Print();
	}
	public HuffmanTree peekMinElement()
	{
		HuffmanTree tempNode=binaryHeap[0];
		return tempNode;
	}
	
	public HuffmanTree deleteMinElement()
	{
		HuffmanTree minNode=binaryHeap[0];
		delete(0);
		return minNode;
	}
	
	public void delete(int index)
	{
		binaryHeap[index]=binaryHeap[binaryHeapSize-1];
		binaryHeapSize--;
		heapifyDown(index);
		
		
	}
	
	private void heapifyUp(int index)
	{
		int nodeFrequency=binaryHeap[index].frequency;
		HuffmanTree node=binaryHeap[index];
		
		while(index>0 && nodeFrequency<=binaryHeap[getParent(index)].frequency)
		{
			binaryHeap[index]=binaryHeap[getParent(index)];
			index=getParent(index);
		}
		binaryHeap[index]=node;
		
	}
	private int getnthChildIndex(int i, int n)
	{
		return 2 * i + n;
	}
	
	private void heapifyDown(int i) 
	{
		int child;
		HuffmanTree tmp = binaryHeap[i];
		while(getnthChildIndex(i, 1) < binaryHeapSize){
			child = minchild(i);
			if(tmp.frequency > binaryHeap[child].frequency)
				binaryHeap[i] = binaryHeap[child];
			else
				break;
			i = child;
		}
		binaryHeap[i] = tmp;
	}
	
	private int minchild(int i) 
	{
		int smallest = getnthChildIndex(i, 1);
		int k = 2;
		int pos = getnthChildIndex(i, k);
		while((k <= 2) && (pos < binaryHeapSize)){
			if(binaryHeap[pos].frequency < binaryHeap[smallest].frequency)
				smallest = pos;
			pos = getnthChildIndex(i, k++);
		}
		return smallest;
	}
	
	public void Print()
	{
		for(int i=0;i<binaryHeapSize;i++)
		{
			System.out.println(binaryHeap[i].frequency + "\n");
		}
	}
}
