# Scenario I #
  1. OSFE starts processing the feed file.
  1. OSFE fails the feed file.
  1. Feed is moved to a retry state.
  1. OSFE attempts to process the feed file again.
  1. OSFE fails the feed file.
  1. Feed is moved to a rejected state.

| **Sequence** | **Feed File Id** | **Feed Job Id** | **Feed File State** | **Feed Job State** | **Feed File Directory** |
|:-------------|:-----------------|:----------------|:--------------------|:-------------------|:------------------------|
|              |                  |                 |                     |                    | /incoming               |
| 1            | 10001            | 20001           | processing          | active             | /workarea               |
| 2            | 10001            | 20001           | failed              | failed             | /failed                 |
| 3            | 10001            | 20001           | retry               | resolved           | /incoming               |
| 4            | 10001            | 20002           | processing          | active             | /workarea               |
| 5            | 10001            | 20002           | failed              | failed             | //failed                |
| 6            | 10001            | 20002           | rejected            | resolved           | /rejected               |