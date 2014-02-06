package com.odea;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odea.components.ajax.AjaxCSVDownloadLink;
import com.odea.components.jquery.datatable.DatatableConfiguration;
import com.odea.components.jquery.datatable.JQueryBasicDataTable;
import com.odea.components.jquery.datatable.column.ActionColumn;
import com.odea.components.jquery.datatable.column.AttributeColumn;
import com.odea.dao.TicketDAO;
import com.odea.domain.Ticket;
import com.odea.filter.Condition;
import com.odea.filter.ConditionPanel;
import com.odea.filter.Field;

/**
 * User: pbergonzi
 * Date: 18/06/12
 * Time: 18:21
 */
public class Grid extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(Grid.class);
    private transient TicketDAO ticketDAO = TicketDAO.getInstance();
    private static final String[] columnas = new String[]{"id", "title", "description", "type", "status"};
    private List<Condition> condiciones;

    public Grid() {
        logger.debug("Loading big grid");

        AjaxCSVDownloadLink boton = new AjaxCSVDownloadLink("boton") {
            @Override
            protected String getFileName() {
                return "pepe.csv";
            }

            @Override
            protected String getCsvData() {
                return "pepe,loco,101,2020";
            }
        };
        this.add(boton);

        final JQueryBasicDataTable<Ticket> tabla = new JQueryBasicDataTable<Ticket>("tabla") {

			@Override
			public List<ActionColumn> getActionColumns() {
				//Columnas de edicion, borrado, etc. En este caso no hay ninguna.
				return new ArrayList<ActionColumn>();
			}

			@Override
			public List<AttributeColumn> getAttributeColumns() {
				
				List<AttributeColumn> columns = new ArrayList<AttributeColumn>();
				
				columns.add(new AttributeColumn("id"));
				columns.add(new AttributeColumn("title"));
				columns.add(new AttributeColumn("type"));
				columns.add(new AttributeColumn("status"));
				
				return columns;
			}

			@Override
			public DatatableConfiguration getDatatableConfiguration() {
				return new DatatableConfiguration();
			}

			@Override
			public Collection<Ticket> getSearchResults() {
				return Grid.this.getTickets();
			}

			@Override
			public void onRequestParameters(Request arg0, AjaxRequestTarget arg1) {
				//Este metodo realiza acciones con parametros que llegan desde el Javascript.
				//Por ejemplo, se selecciona el boton de editar y llegan a este metodo los 
				//datos del objeto a modificar.
			}

            
        };

        Panel advancedfilter = new ConditionPanel("advancedfilter") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, List<Condition> conditions) {
                Grid.this.condiciones = conditions;
                //target.appendJavaScript("reload();");
                target.add(Grid.this.getPage());
                target.focusComponent(this);
            }

            @Override
            protected List<Field> getFields() {
                return Grid.this.getCamposFiltro();
            }
        };

        add(advancedfilter);
        tabla.setOutputMarkupId(true);
        add(tabla);

    }

    private Collection<Ticket> getTickets() {
        if (this.condiciones != null && !this.condiciones.isEmpty()) {
            return ticketDAO.getTickets(this.condiciones);
        }
        return ticketDAO.getTickets();
    }

    private List<Field> getCamposFiltro() {
        List<Field> fields = new ArrayList<Field>(5);

        fields.add(new Field("id", Field.Type.NUMERIC, "id"));
        fields.add(new Field("title", Field.Type.TEXT, "title"));
        fields.add(new Field("type", Field.Type.TEXT, "type"));
        fields.add(new Field("status", Field.Type.TEXT, "status"));

        return fields;
    }
}
