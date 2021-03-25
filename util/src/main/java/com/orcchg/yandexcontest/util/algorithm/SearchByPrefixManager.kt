package com.orcchg.yandexcontest.util.algorithm

class SearchByPrefixManager(dictionary: Collection<String>, private val ignoreCase: Boolean) {

    private val trie = Trie(dictionary)

    fun addWord(word: String) {
        trie.addWord(word)
    }

    fun size(): Int = trie.size()

    fun contains(word: String): Boolean =
        if (ignoreCase) trie.containsIgnoreCase(word) else trie.contains(word)

    fun findByPrefix(prefix: String): Collection<String> =
        if (ignoreCase) trie.startsWithIgnoreCase(prefix) else trie.startsWith(prefix)

    // -------------------- Algorithm --------------------
    private data class TrieArc(val char: Char, val node: TrieNode)
    private data class TrieNode(
        val arcs: MutableList<TrieArc> = mutableListOf(),
        var isTerminal: Boolean = false
    ) {
        fun next(c: Char): TrieArc? = arcs.find { it.char == c }
        fun getOrAdd(c: Char): TrieArc =
            next(c) ?: run {
                val nextNode = TrieNode()
                TrieArc(c, nextNode).also { arcs.add(it) }
            }
    }
    private data class Frame(val node: TrieNode, val prefix: String)

    private class Trie(dictionary: Collection<String>) {

        private var size: Int = 0

        private val root = TrieNode(
            arcs = mutableListOf(),
            isTerminal = false
        )

        private val refinedDictionary: Set<String> = dictionary.toSet() // no duplicates

        init { // build trie
            refinedDictionary.forEach(::addWord)
        }

        fun size(): Int = size

        fun contains(word: String): Boolean {
            var node = root
            word.forEach { c ->
                val arc = node.next(c) ?: return false
                node = arc.node
            }
            return node.isTerminal
        }

        fun containsIgnoreCase(word: String): Boolean {
            fun helper(node: TrieNode, word: String, index: Int): Boolean {
                if (word.length == index) {
                    return node.isTerminal
                }

                var result = true
                val char = word[index]
                if (char.isLetter()) {
                    val low = node.next(char.toLowerCase())?.node?.let { helper(it, word, index + 1) } ?: false
                    val up = node.next(char.toUpperCase())?.node?.let { helper(it, word, index + 1) } ?: false
                    result = result && (low || up)
                } else {
                    val any = node.next(char)?.node?.let { helper(it, word, index + 1) } ?: false
                    result = result && any
                }
                return result
            }

            return helper(root, word, 0)
        }

        fun startsWith(prefix: String): Collection<String> {
            val pathPrefix = StringBuilder()
            var node = root
            prefix.forEach { c ->
                val arc = node.next(c) ?: return emptySet() // no results
                pathPrefix.append(arc.char)
                node = arc.node
            }
            val suffixes = traverseAllPathsFrom(node)
            return suffixes.map { pathPrefix.toString() + it }
        }

        fun startsWithIgnoreCase(prefix: String): Collection<String> {
            fun helper(frame: Frame, prefix: String, index: Int, output: MutableCollection<Frame>) {
                if (prefix.length == index) {
                    output.add(frame)
                    return
                }

                val node = frame.node
                val char = prefix[index]
                if (char.isLetter()) {
                    val lowChar = char.toLowerCase()
                    node.next(lowChar)?.node?.let {
                        val newFrame = Frame(node = it, frame.prefix + lowChar)
                        helper(newFrame, prefix, index + 1, output)
                    }

                    val upChar = char.toUpperCase()
                    node.next(upChar)?.node?.let {
                        val newFrame = Frame(node = it, frame.prefix + upChar)
                        helper(newFrame, prefix, index + 1, output)
                    }
                } else {
                    node.next(char)?.node?.let {
                        val newFrame = Frame(node = it, frame.prefix + char)
                        helper(newFrame, prefix, index + 1, output)
                    }
                }
            }

            val frames = mutableListOf<Frame>()
            helper(Frame(root, prefix = ""), prefix, 0, frames)

            val result = mutableSetOf<String>()
            frames.forEach { frame ->
                val suffixes = traverseAllPathsFrom(frame.node)
                suffixes.map { frame.prefix + it }.let(result::addAll)
            }
            return result
        }

        fun addWord(word: String) {
            var node = root
            word.forEach { c ->
                val arc = node.getOrAdd(c)
                node = arc.node
            }
            if (!node.isTerminal) {
                ++size
            }
            node.isTerminal = true
        }

        private fun traverseAllPathsFrom(startNode: TrieNode): Collection<String> {
            val result = mutableSetOf<String>()
            if (startNode.arcs.isNotEmpty()) {
                val stack = ArrayDeque<Frame>()
                stack.addLast(Frame(node = startNode, prefix = ""))

                while (stack.isNotEmpty()) {
                    val frame = stack.removeLast()

                    if (frame.node.isTerminal || frame.node.arcs.isEmpty()) {
                        result.add(frame.prefix)
                    }

                    frame.node.arcs.forEach { arc ->
                        val nextFrame = Frame(node = arc.node, prefix = frame.prefix + arc.char)
                        stack.addLast(nextFrame)
                    }
                }
            }
            return result
        }
    }
}
