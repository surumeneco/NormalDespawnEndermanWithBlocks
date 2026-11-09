---
cat_version: '0.3'
kind: pi
id: ndew.enderman.despawn.pi.scan
status: draft
process: ndew.enderman.despawn
source_refs:
  - https://drive.google.com/file/d/1CISIkE1HL_k_zpsgDf3KEEJfM-soT7wl/view
decision_ref: ''
refs: []
semantic_contract: markdown-v2
language: ja
---

# PI: ndew.enderman.despawn.pi.scan

## 境界
| 相手Process | 方向 |
| --- | --- |
| minecraft.loaded-entity-world | 双方向 |

## 項目
| ID | 方向 | Domain | 存在 |
| --- | --- | --- | --- |
| 入力.ブロック保持 | 受信 | 真偽 | 必須 |
| 入力.自然消滅許可 | 受信 | 真偽 | 必須 |
| 入力.特別保護 | 受信 | 真偽 | 必須 |
| 入力.ピースフル | 受信 | 真偽 | 必須 |
| 入力.最短距離二乗 | 受信 | 実数[0..] | 必須 |
| 出力.削除 | 送信 | 真偽 | 必須 |

## 操作
| ID | 方向 | 起点 |
| --- | --- | --- |
| check | 受付 | スキャン |

## 制約
| 式 |
| --- |
| `入力.最短距離二乗 >= 0` |

## 未確定点

- 「最短距離二乗」の無限大（近傍プレイヤー不在）は通常の実数Domain外。正式なPresence/追加フラグを採用してモデル化する必要がある。
