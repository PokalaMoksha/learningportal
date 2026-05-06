package com.learning.certificate.service;

import com.learning.common.dto.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CertificateService {

    

    CertificateResponse uploadCertificate(
                        CertificateRequest request,
                        String username)
                        throws IOException;


    List<CertificateResponse>
           getCertificatesByEmployee(
           Long employeeId);

    InputStream downloadCertificate(
                Long certificateId)
                throws IOException;
}
