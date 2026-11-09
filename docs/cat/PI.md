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
| 入力.保護対象 | 受信 | 真偽 | 必須 |
| 入力.ピースフル | 受信 | 真偽 | 必須 |
| 入力.プレイヤー有 | 受信 | 真偽 | 必須 |
| 入力.ハード超過 | 受信 | 真偽 | 必須 |
| 入力.ソフト以下 | 受信 | 真偽 | 必須 |
| 入力.抽選成立 | 受信 | 真偽 | 必須 |
| 出力.削除 | 送信 | 真偽 | 必須 |
| 出力.経過リセット | 送信 | 真偽 | 必須 |

## 操作
| ID | 方向 | 起点 |
| --- | --- | --- |
| check | 受付 | スキャン |

## 制約
| 式 |
| --- |
