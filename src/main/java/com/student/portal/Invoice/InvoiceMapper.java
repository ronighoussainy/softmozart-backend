package com.student.portal.Invoice;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    Invoice invoiceDtoToInvoice(InvoiceDto invoiceDto);

    InvoiceDto invoiceToInvoiceDto(Invoice invoice);
}
