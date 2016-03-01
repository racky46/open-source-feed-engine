# Feed Directory Structure #
OSFE uses a specific directory structure for managing a feed file.  The directory structure provides a contract for the life time of a feed file from where the file should be downloaded to where the file is permanently archived.

# Inbound Feed Directory Structure #

The following table describes the OSFE inbound directory structure.

| **Name** | **Description** |
|:---------|:----------------|
| archive  | Where feeds are moved once successfully completed. |
| download | Where inbound feeds should be downloaded or created. |
| failed   | Where feeds are moved to when a feed fails to complete processing. |
| incoming | Where inbound feeds should be moved to once ready for processing. |
| original | Where the original version of a feed file should be moved. |
| preprocess | Where feeds are prepared for incoming (i.e. Unzipping a feed file). |
| rejected | Where feeds are moved to when they fail and are rejected. |
| temp     | Where temporary files are built and destroyed during feed processing. |
| workarea | Where the feed files are moved when they are being processed. |



# Outbound Feed Directory Structure #

The following table describes the OSFE outbound directory structure.

| **Name** | **Description** |
|:---------|:----------------|
| archive  | Where feeds may be moved once removed from outgoing. |
| failed   | Where feeds are moved to when a feed fails to be created successfully. |
| outgoing | Where outbound feeds should be moved to once ready for processing. |
| postprocess | Where feeds are prepared for outgoing (i.e. zipping a feed file). |
| rejected | Where feeds are moved to when they fail and are rejected. |
| temp     | Where temporary files are built and destroyed during feed creation. |
| workarea | Where the feed files are moved when they are being created. |