package com.odea;


import com.odea.components.ajax.AjaxCSVDownloadLink;
import com.odea.components.jquery.datatable.JQueryBasicDataTable;
import com.odea.dao.TicketDAO;
import com.odea.domain.Ticket;
import com.odea.filter.Condition;
import com.odea.filter.ConditionPanel;
import com.odea.filter.Field;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
            public Collection<Ticket> getSearchResults(String searchToken) {
                return Grid.this.getTickets();
            }

            @Override
            public String[] getColumns() {
                return columnas;
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
