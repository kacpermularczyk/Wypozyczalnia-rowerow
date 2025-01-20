package rental.all;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import rental.dao.BikeDAO;
import rental.entities.Bike;

public class BikeLazyDataModel extends LazyDataModel<Bike> {

	private BikeDAO bikeDAO;
	Map<String,Object> searchParams;
	
	public BikeLazyDataModel(BikeDAO bikeDAO, Map<String,Object> searchParams) {
		this.bikeDAO = bikeDAO;
		this.searchParams = searchParams;
	}
	
	@Override
	public int count(Map<String, FilterMeta> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Bike> load(int first, int pageSize, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
		
		System.out.println("(Z BikeLazyDataModel) Pierwszy rekord = " + first + ", rozmiar = " + pageSize); //sprawdzenie czy lazy dziala
		
		List<Bike> bikes = bikeDAO.findRange(first, pageSize, searchParams);
		
		this.setRowCount( (int) bikeDAO.count(searchParams) );
		
		return bikes;
	}

}
