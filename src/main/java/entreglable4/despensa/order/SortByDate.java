package entreglable4.despensa.order;

import java.util.Comparator;

import entregable4.despensa.entities.ItemPedido;

public class SortByDate implements Comparator<ItemPedido>{

	@Override
	public int compare(ItemPedido p1, ItemPedido p2) {
		return p1.getPedido().getMomentoCompra().compareTo(p2.getPedido().getMomentoCompra());
	}

}
