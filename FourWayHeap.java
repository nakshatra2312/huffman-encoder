
class FourWayHeap
{
	private int fourWayHeapsize;
	private HuffmanTree[] fourWayHeap;
	
	public FourWayHeap(int capacity){
		fourWayHeapsize = 0;
		fourWayHeap = new HuffmanTree[capacity + 4];
		
	}
	
	public int size(){
		return fourWayHeapsize;
	}
	
	public boolean isEmpty(){
		return fourWayHeapsize == 0;
	}
	
	private int getParent(int i){
		return i/4 + 2;
	}
	
	private int getkthChild(int i, int k){
		return (4*i - 9) + k;
	}
	
	public void insertToFourWayHeap(HuffmanTree x){
		fourWayHeap[fourWayHeapsize+3] = x;
		fourWayHeapsize++;
		heapifyUp(fourWayHeapsize + 3 - 1);
	}
	
	private void heapifyUp(int childInd) {
		HuffmanTree tmp = fourWayHeap[childInd];
		while(childInd > 3 && tmp.frequency < fourWayHeap[getParent(childInd)].frequency){
			fourWayHeap[childInd] = fourWayHeap[getParent(childInd)];
			childInd = getParent(childInd);
		}  
		fourWayHeap[childInd] = tmp;
	} 
	
	public HuffmanTree peek(){
		return fourWayHeap[3];
	}
	
	public HuffmanTree poll(){
		HuffmanTree item = fourWayHeap[3];
		delete(3);
		return item;
	}

	private void delete(int i) {
		fourWayHeap[i] = fourWayHeap[fourWayHeapsize + 3 - 1];
		fourWayHeapsize--;
		heapifyDown(i);
	}

	private void heapifyDown(int i) {
		int child;
		HuffmanTree tmp = fourWayHeap[i];
		while(getkthChild(i, 1) < fourWayHeapsize+4){
			child = minchild(i);
			if(tmp.frequency > fourWayHeap[child].frequency)
				fourWayHeap[i] = fourWayHeap[child];
			else
				break;
			i = child;
		}
		fourWayHeap[i] = tmp;
	}

	private int minchild(int i) {
		int smallest = getkthChild(i, 1);
		int k = 2;
		int pos = getkthChild(i, k);
		while((k <= 4) && (pos < fourWayHeapsize + 4)){
			if(fourWayHeap[pos].frequency < fourWayHeap[smallest].frequency)
				smallest = pos;
			k++;
			pos = getkthChild(i, k);
		}
		return smallest;
	}
		public void Print()
	{
		System.out.println("Heap");
		for(int i=3;i<fourWayHeapsize+3;i++)
		{
			System.out.println(fourWayHeap[i].frequency);
		}
	}
	
}
