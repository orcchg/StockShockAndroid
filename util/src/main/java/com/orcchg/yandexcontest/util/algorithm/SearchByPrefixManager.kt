package com.orcchg.yandexcontest.util.algorithm

import java.lang.StringBuilder
import java.util.*

class SearchByPrefixManager(dictionary: Collection<String>, ignoreCase: Boolean) {

    private val trie = Trie(dictionary, ignoreCase)

    fun contains(word: String): Boolean = trie.contains(word)
    fun findByPrefix(prefix: String): Collection<String> = trie.startsWith(prefix)

    // -------------------- Algorithm --------------------
    private data class TrieArc(val char: Char, val node: TrieNode)
    private data class TrieNode(
        val arcs: MutableList<TrieArc> = mutableListOf(),
        val isTerminal: Boolean
    ) {
        fun next(c: Char): TrieArc? = arcs.find { it.char == c }
        fun getOrAdd(c: Char, isTerminal: Boolean = false): TrieArc =
            next(c) ?: run {
                val nextNode = TrieNode(isTerminal = isTerminal)
                TrieArc(c, nextNode).also { arcs.add(it) }
            }
    }

    private class Trie(
        dictionary: Collection<String>,
        private val ignoreCase: Boolean
    ) {

        private val root = TrieNode(
            arcs = mutableListOf(),
            isTerminal = false
        )

        private val refinedDictionary: Set<String> = dictionary.toSet() // no duplicates

        init { // build trie
            refinedDictionary.forEach(::addWord)
        }

        fun contains(word: String): Boolean {
            var node = root
            val theWord = if (ignoreCase) word.toUpperCase(Locale.ROOT) else word
            theWord.forEach { c ->
                val arc = node.next(c) ?: return false
                node = arc.node
            }
            return node.isTerminal
        }

        fun startsWith(prefix: String): Collection<String> {
            val pathPrefix = StringBuilder()
            var node = root
            val thePrefix = if (ignoreCase) prefix.toUpperCase(Locale.ROOT) else prefix
            thePrefix.forEach { c ->
                val arc = node.next(c) ?: return emptySet() // no results
                pathPrefix.append(arc.char)
                node = arc.node
            }
            val suffixes = traverseAllPathsFrom(node)
            return suffixes.map { pathPrefix.toString() + it }
        }

        private fun addWord(word: String) {
            var node = root
            val theWord = if (ignoreCase) word.toUpperCase(Locale.ROOT) else word
            theWord.forEachIndexed { index, c ->
                val isTerminal = (index + 1) == word.length // last char
                val arc = node.getOrAdd(c, isTerminal)
                node = arc.node
            }
        }

        private data class Frame(val node: TrieNode, val prefix: String)

        private fun traverseAllPathsFrom(startNode: TrieNode): Collection<String> {
            val result = mutableSetOf<String>()
            if (startNode.arcs.isNotEmpty()) {
                val stack = Stack<Frame>()
                stack.push(Frame(node = startNode, prefix = ""))

                while (stack.isNotEmpty()) {
                    val frame = stack.pop()

                    if (frame.node.isTerminal || frame.node.arcs.isEmpty()) {
                        result.add(frame.prefix)
                    }

                    frame.node.arcs.forEach { arc ->
                        val nextFrame = Frame(node = arc.node, prefix = frame.prefix + arc.char)
                        stack.push(nextFrame)
                    }
                }
            }
            return result
        }
    }
}
