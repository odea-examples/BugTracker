package com.odea.filter;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import java.util.Arrays;
import java.util.List;

/**
 * User: pbergonzi
 * Date: 05/07/12
 * Time: 15:25
 */

abstract class ConditionForm extends Form<Condition> {
    IModel<Condition> conditionModel = new CompoundPropertyModel<Condition>(new Condition());

    public ConditionForm(String id) {
        super(id);
        this.setDefaultModel(conditionModel);
        DropDownChoice<Field> field = new DropDownChoice<Field>("field", this.getFields(), new FieldChoiceRender());
        field.setRequired(true);

        DropDownChoice<Operator> operator = new DropDownChoice<Operator>("operator", Arrays.asList(Operator.values()));
        operator.setRequired(true);

        RequiredTextField<String> value = new RequiredTextField<String>("value");

        AjaxButton submit = new AjaxButton("submit", this) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                ConditionForm.this.onSubmit(target, (Form<Condition>) form);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                ConditionForm.this.onError(target, (Form<Condition>) form);
            }

        };

        add(field);
        add(operator);
        add(value);
        add(submit);
        setOutputMarkupId(true);
    }


    protected class FieldChoiceRender implements IChoiceRenderer<Field> {
        public Object getDisplayValue(Field object) {
            return object.getName();
        }

        public String getIdValue(Field object, int index) {
            return object.getName() + "_" + index;
        }
    }

    protected abstract List<Field> getFields();

    protected abstract void onSubmit(AjaxRequestTarget target, Form<Condition> form);

    protected abstract void onError(AjaxRequestTarget target, Form<Condition> form);
}
