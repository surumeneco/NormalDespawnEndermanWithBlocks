---
cat_version: '0.3'
kind: tce
id: ndew.enderman.despawn.tce.rules
status: confirmed
process: ndew.enderman.despawn
source_refs:
  - https://drive.google.com/file/d/1CISIkE1HL_k_zpsgDf3KEEJfM-soT7wl/view
decision_ref: https://github.com/surumeneco/NormalDespawnEndermanWithBlocks/blob/c66715e10e8ca32bc7a1b83e0e2c57bbbac1bd9d/docs/cat/decisions/2026-11-09-user-confirmed-authorization.md
refs: 
  - ndew.enderman.despawn.pi.scan
semantic_contract: markdown-v2
language: ja
---

# TCE: ndew.enderman.despawn.tce.rules

網羅要求: closed

## 振る舞い
| ID | 起点 | 条件 | 結果 | 効果 |
| --- | --- | --- | ---: | --- |
| non-carrier | スキャン | `入力.ブロック保持 == 偽` | 1 | `出力.削除 = 偽; 出力.経過リセット = 真` |
| protected | スキャン | `入力.ブロック保持 == 真 かつ 入力.保護対象 == 真` | 1 | `出力.削除 = 偽; 出力.経過リセット = 真` |
| peaceful | スキャン | `入力.ブロック保持 == 真 かつ 入力.保護対象 == 偽 かつ 入力.ピースフル == 真` | 1 | `出力.削除 = 真; 出力.経過リセット = 真` |
| no-player | スキャン | `入力.ブロック保持 == 真 かつ 入力.保護対象 == 偽 かつ 入力.ピースフル == 偽 かつ 入力.プレイヤー有 == 偽` | 1 | `出力.削除 = 偽; 出力.経過リセット = 真` |
| beyond-hard | スキャン | `入力.ブロック保持 == 真 かつ 入力.保護対象 == 偽 かつ 入力.ピースフル == 偽 かつ 入力.プレイヤー有 == 真 かつ 入力.ハード超過 == 真` | 1 | `出力.削除 = 真; 出力.経過リセット = 真` |
| within-soft | スキャン | `入力.ブロック保持 == 真 かつ 入力.保護対象 == 偽 かつ 入力.ピースフル == 偽 かつ 入力.プレイヤー有 == 真 かつ 入力.ハード超過 == 偽 かつ 入力.ソフト以下 == 真` | 1 | `出力.削除 = 偽; 出力.経過リセット = 真` |
| middle-success | スキャン | `入力.ブロック保持 == 真 かつ 入力.保護対象 == 偽 かつ 入力.ピースフル == 偽 かつ 入力.プレイヤー有 == 真 かつ 入力.ハード超過 == 偽 かつ 入力.ソフト以下 == 偽 かつ 入力.抽選成立 == 真` | 1 | `出力.削除 = 真; 出力.経過リセット = 真` |
| middle-failure | スキャン | `入力.ブロック保持 == 真 かつ 入力.保護対象 == 偽 かつ 入力.ピースフル == 偽 かつ 入力.プレイヤー有 == 真 かつ 入力.ハード超過 == 偽 かつ 入力.ソフト以下 == 偽 かつ 入力.抽選成立 == 偽` | 1 | `出力.削除 = 偽; 出力.経過リセット = 偽` |

## 範囲と限界

8条件で削除・保持・遠距離経過のリセットの決定領域を分割する。原始入力の算出、Paperの距離形状、確率集約、外部プレイヤー選別はこのTCEの外部にある。数値計算はDrive仕様とJava単体テストで別途照合し、全製品実装適合性の保証には代用しない。
