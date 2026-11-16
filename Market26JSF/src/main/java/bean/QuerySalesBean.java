package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("querySalesBean")
@ViewScoped
public class QuerySalesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bilaketa = "";
	private List<Sale> salmentak;
	private Sale aukeratua;
	private String mezua;

	public String bilatu() {
		aukeratua = null;
		try {
			BLFacade facade = FacadeBean.getBusinessLogic();
			Date today = UtilDate.trim(new Date());
			salmentak = facade.getPublishedSales(bilaketa, today);
			mezua = salmentak.isEmpty() ? "Ez da produkturik aurkitu" : "";
		} catch (Exception e) {
			e.printStackTrace();
			mezua = e.getMessage() != null ? e.getMessage() : e.toString();
		}
		return null;
	}

	public String aukeratu(Sale s) {
		this.aukeratua = s;
		return null;
	}

	public String getBilaketa() { return bilaketa; }
	public void setBilaketa(String bilaketa) { this.bilaketa = bilaketa; }

	public List<Sale> getSalmentak() { return salmentak; }

	public Sale getAukeratua() { return aukeratua; }

	public String getMezua() { return mezua; }
}