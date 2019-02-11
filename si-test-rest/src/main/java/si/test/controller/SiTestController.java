package si.test.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import si.test.service.SiTestService;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping(value = "/si-test")
@Validated
public class SiTestController {

    public static final String VALIDATION_REGEX = "^[^,\\n]+,\\d+(?:,[^,\\n]+){2}$";

    private final SiTestService siTestService;

    @Autowired
    public SiTestController(final SiTestService SiTestService) {
        this.siTestService = SiTestService;
    }

    @GetMapping(value = "/check")
    @ApiOperation(value = "Checks login", response = Boolean.class)
    public String check(@Valid @ApiParam(value = "Login line") @Pattern(regexp = VALIDATION_REGEX) @RequestParam(required = true) final String line) {
        return siTestService.parseLine(line);
    }

    @GetMapping(value = "/interval")
    @ApiOperation(value = "Gets interval in minutes between two timestamps", response = Boolean.class)
    public long getInterval(@ApiParam(value = "Timestamp 1") @RequestParam(required = true) final String time1,
                            @ApiParam(value = "Timestamp 2") @RequestParam(required = true) final String time2) {
        return siTestService.getInterval(time1, time2);
    }
}
