package com.odea.filter;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * User: pbergonzi
 * Date: 04/07/12
 * Time: 14:26
 */

public abstract class ConditionPanel extends Panel {
    private List<Condition> conditions = new ArrayList<Condition>();
    IModel<List<Condition>> conditionsModel = new PropertyModel<List<Condition>>(this, "conditions");

    public ConditionPanel(String id) {
        super(id);
        this.setOutputMarkupId(true);
        final WebMarkupContainer conditionsContainer = new WebMarkupContainer("conditionsContainer");

        ConditionForm conditionForm = new ConditionForm("conditionForm") {
            @Override
            protected List<Field> getFields() {
                return ConditionPanel.this.getFields();
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<Condition> form) {
                target.add(conditionsContainer);
                Condition condition = form.getModelObject().clone();
                conditions.add(condition);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<Condition> form) {

            }
        };

        ListView<Condition> conditionLst = new ListView<Condition>("conditionLst", conditionsModel) {
            @Override
            protected void populateItem(ListItem<Condition> components) {
                final Condition condition = components.getModelObject();
                components.add(new Label("condition", condition.getCondition()));
                AjaxLink eraseCondition = new AjaxLink("eraseCondition") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        ConditionPanel.this.conditions.remove(condition);
                        target.add(conditionsContainer);
                    }

                };
                components.add(eraseCondition);
            }
        };

        conditionsContainer.add(conditionLst);
        conditionsContainer.setOutputMarkupId(true);

        add(conditionForm);
        add(conditionsContainer);

        AjaxLink execFilter = new AjaxLink("executeFilter") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                ConditionPanel.this.onSubmit(target, ConditionPanel.this.conditions);
            }

        };

        add(execFilter);

        AjaxLink cleanFilter = new AjaxLink("cleanFilter") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                ConditionPanel.this.conditions.clear();
                target.add(conditionsContainer);
            }

        };

        add(cleanFilter);
    }


    protected abstract void onSubmit(AjaxRequestTarget target, List<Condition> conditions);

    //protected abstract IAjaxCallDecorator getAjaxCallDecorator();

    protected abstract List<Field> getFields();
}
