
class HuffmanDecoderTree
{
	String data;
	HuffmanDecoderTree left;
	HuffmanDecoderTree right;
	public HuffmanDecoderTree()
	{
		this.data=null;
	}
	
	public HuffmanDecoderTree(String data)
	{
		this.data=data;
	}

	public void data(String data) {
		this.data=data;
		
	}
}