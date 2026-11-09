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
閉世界: 偽

## 境界
| PI |
| --- |
| ndew.enderman.despawn.pi.scan |

## 仕様状態
| ID | Domain |
| --- | --- |
| 状態.遠距離経過 | 整数[0..] |

## 観測能力
| ID | 情報源 | Domain |
| --- | --- | --- |
| 観測.近傍プレイヤー | サーバーワールド | 真偽 |
| 観測.最短距離二乗 | サーバーワールド | 実数[0..] |

## 作用能力
| ID | 対象 | Domain |
| --- | --- | --- |
| 作用.エンダーマン削除 | 現在処理対象 | 真偽 |

## 起点
| ID | 由来 |
| --- | --- |
| スキャン | ndew.enderman.despawn.pi.scan/check |

## 未確定点

- 連続確率と半端tick・ワールド境界を、CATの現行限定式と型付きDomainへどう写像するかは未確定。Draftとして保持し、擬似通過を行わない。
