package bean;

import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("querySalesBean")
@RequestScoped
public class QuerySalesBean {

	BLFacade facadeBL = FacadeBean.getBusinessLogic();

	private String bilaketa = "";
	private List<Sale> salmentak;
	private String mezua;

	public String bilatu() {
		try {
			Date today = UtilDate.trim(new Date());
			salmentak = facadeBL.getPublishedSales(bilaketa, today);
			mezua = "";
			if(salmentak.isEmpty()) {
				mezua = "Ez da produkturik aurkitu";
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null && !e.getMessage().isEmpty()) {
	            mezua = e.getMessage();
	        } else {
	            mezua = e.toString();
	        }		}
		return null;
	}

	public String getBilaketa() { 
		return bilaketa; 
	}
	public void setBilaketa(String bilaketa) { 
		this.bilaketa = bilaketa; 
	}

	public List<Sale> getSalmentak() { 
		return salmentak; 
	}

	public String getMezua() { 
		return mezua; 
	}
}