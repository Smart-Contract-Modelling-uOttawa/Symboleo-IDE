package ca.uottawa.csmlab.symboleo.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter;
import org.eclipse.xtext.nodemodel.INode;

public class SymboleoValueConverters extends DefaultTerminalConverters {

  @ValueConverter(rule = "Date")
  public IValueConverter<Date> TimestampValue() {
    return new AbstractNullSafeConverter<Date>() {
      
      @Override
      protected String internalToString(Date value) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return '"' + fmt.format(value) + '"';
      }

      @Override
      protected Date internalToValue(String string, INode node) throws ValueConverterException {
        string = string.substring(6, string.length() - 2);
        try {
          SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
          return fmt.parse(string);
        } catch(Exception e) {
          throw new ValueConverterException("Not in valid format: Use Date(\"yyyy/MM/dd HH:mm:ss\"). Parse error:" + e.getMessage(), node, e);
        }
      }
    };
  }
  
}
