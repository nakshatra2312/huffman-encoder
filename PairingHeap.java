class PairingHeapNode
{
	HuffmanTree node;
	PairingHeapNode childNode;
	PairingHeapNode siblingNode;
	PairingHeapNode prev;
	
	public PairingHeapNode(HuffmanTree tempNode)
	{
		this.node=tempNode;
		childNode=null;
		siblingNode=null;
		prev=null;
		
		
	}
}


class PairingHeap
{
	private PairingHeapNode rootNode;
	private PairingHeapNode[] pairingHeapArray=new PairingHeapNode[1000000];
	
	public PairingHeap()
	{
		rootNode=null;
	}
	public boolean isEmpty()
	{
		return rootNode==null;
	}
	public int size()
	{
		return pairingHeapArray.length;
	}
	
	public PairingHeapNode insertPariningHeap(HuffmanTree node)
	{
		PairingHeapNode tempNode=new PairingHeapNode(node);
		if(rootNode==null)
		{
			rootNode=tempNode;
		}
		else
		{
			rootNode=compareChildAndAdd(rootNode,tempNode);
		}
		return rootNode;
	}
	
	public PairingHeapNode compareChildAndAdd(PairingHeapNode firstNode, PairingHeapNode secondNode)
	{
		if(secondNode==null)
		{
			return firstNode;
		}
		
		if(secondNode.node.frequency<firstNode.node.frequency)
		{
			secondNode.prev=firstNode.prev;
			firstNode.prev=secondNode;
			
			firstNode.siblingNode=secondNode.childNode;
			if(firstNode.siblingNode!=null)
			{
				firstNode.siblingNode.prev=firstNode;
			}
			secondNode.childNode=firstNode;
			return secondNode;
		}
		else
		{
			secondNode.prev=firstNode;
			firstNode.siblingNode=secondNode.siblingNode;
			
			if(firstNode.siblingNode!=null)
			{
				firstNode.siblingNode.prev=firstNode;
			}
			secondNode.siblingNode=firstNode.childNode;
			
			if(secondNode.siblingNode!=null)
			{
				secondNode.siblingNode.prev=secondNode;
			}
			firstNode.childNode=secondNode;
			
			return firstNode;
		}
	}
	
	private PairingHeapNode meldSiblingNodes(PairingHeapNode firstSiblingNode)
	{
		if(firstSiblingNode.siblingNode==null)
		{
			return firstSiblingNode;
		}
		int siblingsCount=0;
		
		for(;firstSiblingNode!=null;siblingsCount++)
		{
			pairingHeapArray=doubleIfFull(pairingHeapArray,siblingsCount);
			pairingHeapArray[siblingsCount]=firstSiblingNode;
			
			firstSiblingNode.prev.siblingNode=null;
			firstSiblingNode=firstSiblingNode.siblingNode;
		}
		
		pairingHeapArray=doubleIfFull(pairingHeapArray,siblingsCount);
		pairingHeapArray[siblingsCount]=null;
		
		int i=0;
		for(;i+1<siblingsCount;i+=2)
		{
			pairingHeapArray[i]=compareChildAndAdd(pairingHeapArray[i],pairingHeapArray[i+1]);
		}
		int j=i-2;
		
		if(j==siblingsCount-3)
		{
			pairingHeapArray[j]=compareChildAndAdd(pairingHeapArray[j],pairingHeapArray[j+2]);
		}
		
		for(;j>=2;j-=2)
		{
			pairingHeapArray[j-2]=compareChildAndAdd(pairingHeapArray[j-2],pairingHeapArray[j]);
		}
		return pairingHeapArray[0];
		
	}
	
	private PairingHeapNode[] doubleIfFull(PairingHeapNode[] array, int index)
	{
		if(index==array.length)
		{
			PairingHeapNode[] oldArray=array;
			array=new PairingHeapNode[index*2];
			for(int i=0;i<index;i++)
			{
				array[i]=oldArray[i];
			}
		}
		return array;
	}
	
	public HuffmanTree deleteMin()
	{
		if(isEmpty())
		{
			return null;
		}
		HuffmanTree minNode=rootNode.node;
		if(rootNode.childNode==null)
		{
			rootNode=null;
		}
		else
		{
			rootNode=meldSiblingNodes(rootNode.childNode);
		}
		return minNode;
			
	}
	
	
}