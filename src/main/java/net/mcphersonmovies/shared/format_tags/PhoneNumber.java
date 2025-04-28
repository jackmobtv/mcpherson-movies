package net.mcphersonmovies.shared.format_tags;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import net.mcphersonmovies.shared.Validators;

import java.io.IOException;

public class PhoneNumber extends TagSupport {
    private String value;
    private String converted = "";

    public void setValue(String value) {
        this.value = value;
    }

    private void convert() {
        // Convert all attributes into a single string
        converted = value;
        if(!Validators.isValidPhone(value)) {
            return; // return the unformatted value
        }
        // Remove all non-digit characters
        String digits = converted.replaceAll("\\D", "");

        // Format into (XXX) XXX-XXXX
        converted = String.format("(%s) %s-%s",
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6, 10));
    }

    public int doEndTag() {
        convert();
        try {
            JspWriter out = pageContext.getOut();
            out.print(converted); // prints the converted result in the JSP
        } catch(IOException ioe) {

        }
        return SKIP_BODY; // Skip the body if the custom tag only has attributes
    }
}
