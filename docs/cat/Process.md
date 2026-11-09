---
cat_version: '0.3'
kind: process
id: ndew.enderman.despawn
status: draft
process: ndew.enderman.despawn
source_refs:
  - https://drive.google.com/file/d/1CISIkE1HL_k_zpsgDf3KEEJfM-soT7wl/view
decision_ref: ''
refs: 
  - ndew.enderman.despawn.pi.scan
semantic_contract: markdown-v2
language: ja
---

# Process: ndew.enderman.despawn

## 契約
閉世界: 真

## 境界
| PI |
| --- |
| ndew.enderman.despawn.pi.scan |

## 仕様状態
| ID | Domain |
| --- | --- |

## 観測能力
| ID | 情報源 | Domain |
| --- | --- | --- |

## 作用能力
| ID | 対象 | Domain |
| --- | --- | --- |

## 起点
| ID | 由来 |
| --- | --- |
| スキャン | ndew.enderman.despawn.pi.scan/check |

## 範囲

自然デスポーンの有限分岐判断。距離の数値算出、tick累積、確率抽選は別途実装・Java単体テストで検証する。外部のワールド・Bukkit APIとデスポーン結果の結合は実機確認対象。
