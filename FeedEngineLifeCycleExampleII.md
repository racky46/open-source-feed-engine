# Scenario II #
  1. OSFE starts processing the feed file.
  1. OSFE successfully completes processing the feed.

| **Sequence** | **Feed File Id** | **Feed Job Id** | **Feed File State** | **Feed Job State** | **Feed File Directory** |
|:-------------|:-----------------|:----------------|:--------------------|:-------------------|:------------------------|
|              |                  |                 |                     |                    | /incoming               |
| 1            | 10002            | 20003           | processing          | active             | /workarea               |
| 2            | 10002            | 20003           | completed           | completed          | /archive                |